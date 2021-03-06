package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.twitch.tmi.servers.Servers;
import com.dankeroni.dankbot.models.TwitchTags;
import com.dankeroni.dankbot.modules.*;
import com.google.gson.Gson;
import org.fusesource.jansi.AnsiConsole;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bot extends PircBot {

    public long timeStarted = System.currentTimeMillis();
    public String botName, oauth, admin, channel, commitHash, branch, path;
    public boolean silentMode, twitchChat, running = false, modded;
    public Random random = new Random();
    public Modules modules = new Modules();
    public Commands commands;
    public Config config;
    public int commitNumber;
    public CustomCommands customCommands;
    public Stop stop;
    public Eval eval;
    public Raffle raffle;
    public Roulette roulette;
    public Points points;
    public Pyramids pyramids;
    public Users users;
    public Api api;
    public Database database;

    public Bot(String path) {
        this.path = path.endsWith("/") ? path : path + "/";
        start();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new Bot("./");
        } else {
            new Bot(args[0]);
        }
    }

    public void start() {
        if (running)
            return;

        running = true;
        AnsiConsole.systemInstall();
        this.log(String.format("Log start: %s %s", Utils.date(), Utils.detailedTime()), LogLevel.DEBUG);
        this.log("Dankbot starting!", LogLevel.INFO);

        config = new Config(this, path + "config.properties");
        config.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel", "db.url", "db.username", "db.password"});
        this.log("Loading config file", LogLevel.DEBUG);
        config.loadConfig();

        if (!config.containsRequiredOptions()) {
            this.log(String.format("Your config file must contain the following values: %s", String.join(", ", (CharSequence[]) config.getRequiredOptions())), LogLevel.ERROR);
            return;
        }

        botName = config.getString("botName").toLowerCase();
        oauth = config.getString("oauth");
        admin = config.getString("admin").toLowerCase();
        channel = "#" + config.getString("channel").toLowerCase();

        silentMode = config.getBoolean("silentMode");
        twitchChat = config.getBoolean("twitchChat", false);

        this.log("Botname: " + config.getString("botName"), LogLevel.DEBUG);
        this.log("Channel: " + config.getString("channel"), LogLevel.DEBUG);
        this.log("Admin: " + config.getString("admin"), LogLevel.DEBUG);

        Utils.setBot(this);
        setName(botName);
        setVerbose(true);

        String ip = "irc.chat.twitch.tv";
        int port = 80;

        try {
            Servers servers = new Gson().fromJson(Utils.readUrl("https://tmi.twitch.tv/servers?channel=".concat(channel.substring(1))), Servers.class);
            ArrayList<String> serverList = servers.servers;
            String fullIp = null;

            for (String server : serverList)
                if (server.endsWith(String.valueOf(6667))) {
                    fullIp = server;
                    break;
                } else fullIp = serverList.get(random.nextInt(serverList.size()));

            String[] ipAndPort = fullIp.split(":");
            ip = ipAndPort[0];
            port = Integer.parseInt(ipAndPort[1]);
        } catch (Exception e) {
            e.printStackTrace();
            this.log("There was a problem fetching the chat server list, using default server", LogLevel.WARN);
        }

        int tries = 0;

        while (!isConnected() && tries++ < 5) {
            try {
                connect(ip, port, oauth);
            } catch (IOException | IrcException e) {
                this.log("Twitch chat servers offline/Check your internet connection/firewall", LogLevel.ERROR);
                e.printStackTrace();
            }
        }

        if (!isConnected()) {
            this.log("Chat servers down!", LogLevel.ERROR);
            return;
        }

        joinChannel(channel);
        this.log("Joined channel", LogLevel.DEBUG);
        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");

        this.log("Loading modules", LogLevel.DEBUG);
        this.loadModules();
        users.loadUsers();

        try {
            commitHash = this.readFromShellCommand("git rev-parse --short HEAD");
            commitNumber = Integer.parseInt(this.readFromShellCommand("git rev-list --count HEAD"));
            this.channelMessage("/me commit " + commitHash + " number " + commitNumber + " joining MrDestructoid");

            branch = readFromShellCommand("git rev-parse --abbrev-ref HEAD");
        } catch (Exception e) {
            this.channelMessage("/me joining MrDestructoid");
        }
        this.log("dankbot started in " + (System.currentTimeMillis() - timeStarted) + "ms", LogLevel.INFO);
    }

    public void loadModules() {
        modules.addModule(database = new Database(this));
        modules.addModule(users = new Users(this));
        modules.addModule(commands = new Commands(this));

        modules.addModule(stop = new Stop(this));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                stop.stop();
            }
        });

        if (config.getBoolean("Raffle", true))
            modules.addModule(raffle = new Raffle(this));

        if (config.getBoolean("Roulette", true))
            modules.addModule(roulette = new Roulette(this));

        if (config.getBoolean("Eval", false))
            modules.addModule(eval = new Eval(this));

        if (config.getBoolean("Points", true))
            modules.addModule(points = new Points(this));

        if (config.getBoolean("Pyramids", true))
            modules.addModule(pyramids = new Pyramids(this));

        if (config.getBoolean("CustomCommands", true))
            modules.addModule(customCommands = new CustomCommands(this));

        if (config.getBoolean("Api", false))
            modules.addModule(api = new Api(this));
    }

    public void channelMessage(String message) {
        this.channelMessage(message, false);
    }

    public void channelMessage(String message, boolean ignoreSilentMode) {
        if ((!silentMode || ignoreSilentMode) && message != null && !message.trim().isEmpty())
            sendMessage(channel, message + " ");
    }

    public void formattedChannelMessage(String message, String sender, String senderMessage, TwitchTags tags) {
        this.channelMessage(Utils.format(message, sender, senderMessage, tags));
    }

    public void onMessageWithTags(String channel, String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        modules.onChannelMessage(message, sender, new TwitchTags(tags));
    }

    public void onUserstateWithTags(String channel, HashMap<String, String> tags) {
        if (channel.equalsIgnoreCase(channel)) {
            boolean currentModded = tags.get("mod").equals("1");

            if (currentModded != this.modded) {
                if (currentModded) this.setMessageDelay(750);
                else this.setMessageDelay(3000);
                this.modded = currentModded;
                this.log("Mod status set", LogLevel.DEBUG);
            }
        }
    }

    public void whisperMessage(String user, String message) {
        this.whisperMessage(user, message, false);
    }

    public void whisperMessage(String user, String message, boolean ignoreSilentMode) {
        if ((!silentMode || ignoreSilentMode) && message != null && user != null && !message.trim().isEmpty() && !user.trim().isEmpty())
            sendMessage("#dankeroni", ".w " + user.toLowerCase() + " " + message);
    }

    public void formattedWhisperMessage(String message, String user, String senderMessage, TwitchTags tags) {
        this.whisperMessage(user, Utils.format(message, user, senderMessage, tags));
    }

    public void onWhisperWithTags(String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        modules.onWhisperMessage(message, sender, new TwitchTags(tags));
    }

    public void ban(String user) {
        this.channelMessage(".ban " + user, true);
    }

    public void unban(String user) {
        this.channelMessage(".unban " + user, true);
    }

    public void timeout(String user, int seconds) {
        this.channelMessage(".timeout " + seconds, true);
    }

    public String readFromShellCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            bufferedReader.close();

            return stringBuilder.toString();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void log(String line) {
        if (line.startsWith("###"))
            this.log(line.substring(3), LogLevel.ERROR);
        else if (line.startsWith("***"))
            this.log(line.substring(3), LogLevel.INFO);
        else
            this.log(line, LogLevel.TRACE);
    }

    public void log(String line, LogLevel logLevel) {
        line = line.trim();

        if (line.isEmpty() || !twitchChat && logLevel == LogLevel.TRACE)
            return;

        System.out.println(Utils.logDate() + " " + Utils.detailedTime() + " " + logLevel + " " + line);
    }

    public Modules getModules() {
        return modules;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public String getAdmin() {
        return admin;
    }

    public Config getConfig() {
        return config;
    }

    public boolean isSilentMode() {
        return silentMode;
    }

    public String getChannel() {
        return channel;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public int getCommitNumber() {
        return commitNumber;
    }

    public String getPath() {
        return path;
    }

    public String getBranch() {
        return branch;
    }

    public CustomCommands getCustomCommands() {
        return customCommands;
    }

    public Stop getStop() {
        return stop;
    }

    public Eval getEval() {
        return eval;
    }

    public Raffle getRaffle() {
        return raffle;
    }

    public Points getPoints() {
        return points;
    }

    public Users getUsers() {
        return users;
    }

    public Commands getCommands() {
        return commands;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Database getDatabase() {
        return database;
    }
}
