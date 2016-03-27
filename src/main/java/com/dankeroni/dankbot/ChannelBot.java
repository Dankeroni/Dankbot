package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.dankeroni.dankbot.modules.*;
import com.google.gson.Gson;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.util.HashMap;

public class ChannelBot extends PircBot {

    private long timeStarted = System.currentTimeMillis();
    private String botName, oauth, admin, channel, configFile;
    private String[] trustedUsers;
    private boolean silentJoinLeave, superCommands, logOutput, running = false;
    private ModuleHandler moduleHandler = new ModuleHandler();
    private WhisperBot whisperBot;
    private Config config;

    public ChannelBot(String configFile) {
        this.configFile = configFile;
        start();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new ChannelBot("config.properties");
        } else {
            for (String configFile : args)
                new ChannelBot(configFile);
        }

    }

    private void start(){
        if(running)
            return;

        running = true;

        config = new Config(configFile);
        config.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel"});
        config.loadConfig();

        if (!config.containsRequiredOptions()) {
            System.out.printf("Your config file must contain the following values: %s", String.join(", ", config.getRequiredOptions()));
            System.exit(1);
        }

        botName = config.getString("botName").toLowerCase();
        oauth = config.getString("oauth");
        admin = config.getString("admin").toLowerCase();
        trustedUsers = config.getStringArray("trustedUsers");
        channel = "#" + config.getString("channel").toLowerCase();

        silentJoinLeave = config.getBoolean("silentJoinLeave");
        superCommands = config.getBoolean("superCommands");
        logOutput = config.getBoolean("logOutput", false);

        System.out.println("Dankbot starting!");
        System.out.println("Botname: " + config.getString("botName"));
        System.out.println("Channel: " + config.getString("channel"));
        System.out.println("Admin: " + config.getString("admin"));

        setName(botName);
        setVerbose(logOutput);
        setMessageDelay(666);

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

        try{
            connect(ip, port, oauth);
        }catch(IOException | IrcException e){
            System.out.println("Twitch chat servers offline/Check your internet connection/firewall");
            e.printStackTrace();
            return;
        }

        joinChannel(channel);
        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");

        whisperBot = new WhisperBot(this, botName, oauth, admin, channel, superCommands, logOutput, moduleHandler);

        moduleHandler.addModule(new BotUpTime(this));
        moduleHandler.addModule(new CustomCommands(this));
        moduleHandler.addModule(new Stop(this));
        moduleHandler.addModule(new Time(this));
        moduleHandler.addModule(new Vanish(this));

        if(!silentJoinLeave)
            channelMessage("/me joining MrDestructoid");
    }

    public void channelMessage(String message){
        if (message != null && !message.trim().isEmpty())
            sendMessage(channel, message);
    }

    public void onMessageWithTags(String channel, String user, String login, String hostname, String message, HashMap<String, String> tags) {
        moduleHandler.checkChannelMessage(message, user, tags);
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
}
