package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.dankeroni.dankbot.modules.CustomCommands;
import com.dankeroni.dankbot.modules.Eval;
import com.dankeroni.dankbot.modules.Stop;
import com.google.gson.Gson;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ChannelBot extends PircBot {

    public long timeStarted = System.currentTimeMillis();
    public String botName, oauth, admin, channel, commitHash, branch, path;
    public String[] trustedUsers;
    public boolean silentJoinLeave, twitchChat, running = false;
    public ModuleHandler moduleHandler = new ModuleHandler();
    public Config config;
    public int commitNumber;
    public CustomCommands customCommands;
    public Stop stop;
    public Eval eval;

    public ChannelBot(String path) {
        this.path = path;
        start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                stop.stopBot();
            }
        });
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new ChannelBot("./");
        } else {
            new ChannelBot(args[0]);
        }
    }

    public void start() {
        if(running)
            return;

        running = true;

        config = new Config(this, path + "config.properties");
        config.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel"});
        config.loadConfig();

        if (!config.containsRequiredOptions()) {
            this.log(String.format("Your config file must contain the following values: %s", String.join(", ", (CharSequence[]) config.getRequiredOptions())));
            System.exit(1);
        }

        botName = config.getString("botName").toLowerCase();
        oauth = config.getString("oauth");
        admin = config.getString("admin").toLowerCase();
        trustedUsers = config.getStringArray("trustedUsers");
        channel = "#" + config.getString("channel").toLowerCase();

        silentJoinLeave = config.getBoolean("silentJoinLeave");
        twitchChat = config.getBoolean("twitchChat", false);

        this.log("Dankbot starting!");
        this.log("Botname: " + config.getString("botName"));
        this.log("Channel: " + config.getString("channel"));
        this.log("Admin: " + config.getString("admin"));

        Utils.setChannelBot(this);
        setName(botName);
        setVerbose(twitchChat);

        String ip = "irc.tchat.twitch.tv";
        int port = 80;

        try {
            Servers servers = new Gson().fromJson(Utils.readUrl("https://tmi.twitch.tv/servers?channel=".concat(channel.substring(1))), Servers.class);
            String[] ipAndPort = servers.randomServer().split(":");
            ip = ipAndPort[0];
            port = Integer.parseInt(ipAndPort[1]);
        } catch (Exception e) {
            e.printStackTrace();
            this.log("There was a problem fetching the chat server list, using default server");
        }

        int tries = 0;

        while (!isConnected() && tries++ < 5) {
            try {
                connect(ip, port, oauth);
            } catch (IOException | IrcException e) {
                this.log("Twitch chat servers offline/Check your internet connection/firewall");
                e.printStackTrace();
                return;
            }
        }

        if (!isConnected()) this.log("Chat servers down!");

        joinChannel(channel);
        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");

        this.loadModules();

        if (!silentJoinLeave) {
            try {
                commitHash = this.readFromShellCommand("git rev-parse --short HEAD");
                commitNumber = Integer.parseInt(this.readFromShellCommand("git rev-list --count HEAD"));
                this.channelMessage("/me commit " + commitHash + " number " + commitNumber + " joining MrDestructoid");

                branch = readFromShellCommand("git branch").substring(2);
            } catch (Exception e) {
                this.channelMessage("/me joining MrDestructoid");
            }
        }
    }

    public void loadModules() {
        moduleHandler.addModule(stop = new Stop(this));

        if (config.getBoolean("CustomCommands", true))
            moduleHandler.addModule(customCommands = new CustomCommands(this));

        if (config.getBoolean("Eval", false))
            moduleHandler.addModule(eval = new Eval(this));
    }

    public void channelMessage(String message){
        if (message != null && !message.trim().isEmpty())
            sendMessage(channel, message + " ");
    }

    public void formattedChannelMessage(String message, String sender, String senderMessage, HashMap<String, String> tags) {
        this.channelMessage(Utils.format(message, sender, senderMessage, tags));
    }

    public void onMessageWithTags(String channel, String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        moduleHandler.checkChannelMessage(message, sender, tags);
    }

    public void onUserstateWithTags(String channel, HashMap<String, String> tags) {
        if (channel.equalsIgnoreCase(channel))
            if (tags.get("mod").equals("1")) this.setMessageDelay(750);
            else this.setMessageDelay(3000);
    }

    public void whisperMessage(String user, String message) {
        if (message != null && user != null && !message.trim().isEmpty() && !user.trim().isEmpty())
            sendMessage("#dankeroni", ".w " + user.toLowerCase() + " " + message);
    }

    public void formattedWhisperMessage(String message, String user, String senderMessage, HashMap<String, String> tags) {
        this.whisperMessage(user, Utils.format(message, user, senderMessage, tags));
    }

    public void onWhisperWithTags(String sender, String login, String hostname, String message, HashMap<String, String> tags) {
        moduleHandler.checkWhisperMessage(message, sender, tags);
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

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
    }

    public long getTimeStarted(){
        return timeStarted;
    }

    public String getAdmin() {
        return admin;
    }

    public Config getConfig() {
        return config;
    }

    public boolean isSilentJoinLeave() {
        return silentJoinLeave;
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
}
