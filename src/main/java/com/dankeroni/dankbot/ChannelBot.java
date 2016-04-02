package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.dankeroni.dankbot.modules.CustomCommands;
import com.dankeroni.dankbot.modules.Stop;
import com.google.gson.Gson;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ChannelBot extends PircBot {

    private long timeStarted = System.currentTimeMillis();
    private String botName, oauth, admin, channel, commitHash, branch, path;
    private String[] trustedUsers;
    private boolean silentJoinLeave, superCommands, twitchChat, running = false;
    private ModuleHandler moduleHandler = new ModuleHandler();
    private WhisperBot whisperBot;
    private Config config;
    private int commitNumber;

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

    private void start(){
        if(running)
            return;

        running = true;

        config = new Config(path + "config.properties");
        config.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel"});
        config.loadConfig();

        if (!config.containsRequiredOptions()) {
            System.out.printf("Your config file must contain the following values: %s", String.join(", ", (CharSequence[]) config.getRequiredOptions()));
            System.exit(1);
        }

        botName = config.getString("botName").toLowerCase();
        oauth = config.getString("oauth");
        admin = config.getString("admin").toLowerCase();
        trustedUsers = config.getStringArray("trustedUsers");
        channel = "#" + config.getString("channel").toLowerCase();

        silentJoinLeave = config.getBoolean("silentJoinLeave");
        superCommands = config.getBoolean("superCommands");
        twitchChat = config.getBoolean("twitchChat", false);

        System.out.println("Dankbot starting!");
        System.out.println("Botname: " + config.getString("botName"));
        System.out.println("Channel: " + config.getString("channel"));
        System.out.println("Admin: " + config.getString("admin"));

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
            System.out.println("There was a problem fetching the chat server list, using default server");
        }

        int tries = 0;

        while (!isConnected() && tries++ < 5) {
            try {
                connect(ip, port, oauth);
            } catch (IOException | IrcException e) {
                System.out.println("Twitch chat servers offline/Check your internet connection/firewall");
                e.printStackTrace();
                return;
            }
        }

        if (!isConnected()) this.log("Chat servers down!");

        joinChannel(channel);
        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");

        whisperBot = new WhisperBot(this, botName, oauth, admin, channel, superCommands, twitchChat, moduleHandler);

        moduleHandler.addModule(new CustomCommands(this));
        moduleHandler.addModule(new Stop(this));

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

    protected void onUserstateWithTags(String channel, HashMap<String, String> tags) {
        if (channel.equalsIgnoreCase(channel))
            if (tags.get("mod").equals("1")) this.setMessageDelay(750);
            else this.setMessageDelay(3000);
    }

    private String readFromShellCommand(String command) {
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

    public WhisperBot getWhisperBot() {
        return whisperBot;
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
}
