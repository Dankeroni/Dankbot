package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.dankeroni.dankbot.modules.*;
import com.google.gson.Gson;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ChannelBot extends PircBot {

    public long timeStarted = System.currentTimeMillis();
    public String botName, oauth, admin, channel, commitHash, branch, path;
    public boolean silentMode, twitchChat, running = false, modded;
    public HashMap<String, AccessLevel> userAccessLevels = new HashMap<>();
    public ModuleHandler moduleHandler = new ModuleHandler();
    public Commands commands;
    public Config config;
    public int commitNumber;
    public CustomCommands customCommands;
    public Stop stop;
    public Eval eval;
    public Raffle raffle;

    public ChannelBot(String path) {
        this.path = path;
        start();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new ChannelBot("./");
        } else {
            new ChannelBot(args[0]);
        }
    }

    public void start() {
        if (running)
            return;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                stop.stop();
            }
        });

        running = true;
        AnsiConsole.systemInstall();
        this.log(String.format("Log start: %s %s", Utils.date(), Utils.detailedTime()), LogLevel.DEBUG);
        this.log("Dankbot starting!", LogLevel.INFO);

        config = new Config(this, path + "config.properties");
        config.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel"});
        this.log("Loading config file", LogLevel.DEBUG);
        config.loadConfig();

        if (!config.containsRequiredOptions()) {
            this.log(String.format("Your config file must contain the following values: %s", String.join(", ", (CharSequence[]) config.getRequiredOptions())), LogLevel.ERROR);
            return;
        }

        botName = config.getString("botName").toLowerCase();
        oauth = config.getString("oauth");
        admin = config.getString("admin").toLowerCase();
        userAccessLevels.put(admin, AccessLevel.ADMIN);
        String[] superModerators = config.getStringArray("trustedUsers");
        for (String trustedUser : superModerators) userAccessLevels.put(trustedUser, AccessLevel.SUPERMODERATOR);
        channel = "#" + config.getString("channel").toLowerCase();

        silentMode = config.getBoolean("silentMode");
        twitchChat = config.getBoolean("twitchChat", false);

        this.log("Botname: " + config.getString("botName"), LogLevel.DEBUG);
        this.log("Channel: " + config.getString("channel"), LogLevel.DEBUG);
        this.log("Admin: " + config.getString("admin"), LogLevel.DEBUG);

        Utils.setChannelBot(this);
        setName(botName);
        setVerbose(true);

        String ip = "irc.chat.twitch.tv";
        int port = 80;

        try {
            Servers servers = new Gson().fromJson(Utils.readUrl("https://tmi.twitch.tv/servers?channel=".concat(channel.substring(1))), Servers.class);
            String[] ipAndPort = servers.randomServer().split(":");
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

        try {
            commitHash = this.readFromShellCommand("git rev-parse --short HEAD");
            commitNumber = Integer.parseInt(this.readFromShellCommand("git rev-list --count HEAD"));
            this.channelMessage("/me commit " + commitHash + " number " + commitNumber + " joining MrDestructoid");

            branch = readFromShellCommand("git branch").substring(2);
        } catch (Exception e) {
            this.channelMessage("/me joining MrDestructoid");
        }
    }

    public void loadModules() {
        moduleHandler.addModule(commands = new Commands(this));
        moduleHandler.addModule(stop = new Stop(this));

        if (config.getBoolean("CustomCommands", true))
            moduleHandler.addModule(customCommands = new CustomCommands(this));

        if (config.getBoolean("Raffle", true))
            moduleHandler.addModule(raffle = new Raffle(this));

        if (config.getBoolean("Eval", false))
            moduleHandler.addModule(eval = new Eval(this));
    }

    public void channelMessage(String message) {
        if (!silentMode && message != null && !message.trim().isEmpty())
            sendMessage(channel, message + " ");
    }

    public void formattedChannelMessage(String message, String sender, String senderMessage, HashMap<String, String> tags) {
        this.channelMessage(Utils.format(message, sender, senderMessage, tags));
    }

    public void onMessageWithTags(String channel, String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        moduleHandler.onChannelMessage(message, sender, tags);
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
        if (!silentMode && message != null && user != null && !message.trim().isEmpty() && !user.trim().isEmpty())
            sendMessage("#dankeroni", ".w " + user.toLowerCase() + " " + message);
    }

    public void formattedWhisperMessage(String message, String user, String senderMessage, HashMap<String, String> tags) {
        this.whisperMessage(user, Utils.format(message, user, senderMessage, tags));
    }

    public void onWhisperWithTags(String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        moduleHandler.onWhisperMessage(message, sender, tags);
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
        if (line.isEmpty()) return;
        Ansi ansi = Ansi.ansi();

        switch (logLevel) {
            case TRACE:
                if (twitchChat)
                    System.out.println(Utils.detailedTime() + " " + ansi.fg(Ansi.Color.WHITE).a("[TRACE]").reset() + " " + line);
                break;

            case DEBUG:
                System.out.println(Utils.detailedTime() + " " + ansi.fg(Ansi.Color.CYAN).a("[DEBUG]").reset() + " " + line);
                break;

            case INFO:
                System.out.println(Utils.detailedTime() + " " + ansi.fg(Ansi.Color.GREEN).a("[INFO]").reset() + " " + line);
                break;

            case WARN:
                System.out.println(Utils.detailedTime() + " " + ansi.fg(Ansi.Color.YELLOW).a("[WARN]").reset() + " " + line);
                break;

            case ERROR:
                System.out.println(Utils.detailedTime() + " " + ansi.fg(Ansi.Color.RED).a("[ERROR]").reset() + " " + line);
                break;
        }
    }

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
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

    public Commands getCommands() {
        return commands;
    }

    public HashMap<String, AccessLevel> getUserAccessLevels() {
        return userAccessLevels;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
