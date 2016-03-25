package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.dankeroni.dankbot.modules.CommandAdd;
import com.dankeroni.dankbot.modules.CommandRemove;
import com.dankeroni.dankbot.modules.Stop;
import com.google.gson.Gson;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.util.HashMap;

public class DankChannelBot extends PircBot{

    public static void main(String[] args){
        if(args.length == 0) {
            new DankChannelBot("config.properties");
        } else {
            for(String configFile: args)
                new DankChannelBot(configFile);
        }

    }

    private long timeStarted = System.currentTimeMillis();
    private String botName, oauth, admin, channel, configFile;
    private String[] trustedUsers;
    private boolean silentJoinLeave, superCommands, logOutput, running = false;
    private DankHandler dankHandler;
    private DankWhisperBot dankWhisperBot;
    private DankConfig dankConfig;

    public DankChannelBot(String configFile){
        this.configFile = configFile;
        start();
    }

    private void start(){
        if(running)
            return;

        running = true;

        dankConfig = new DankConfig(configFile);
        dankConfig.setRequiredOptions(new String[]{"botName", "oauth", "admin", "channel"});
        dankConfig.loadConfig();

        if(!dankConfig.containsRequiredOptions()) {
            System.out.printf("Your config file must contain the following values: %s", String.join(", ", dankConfig.getRequiredOptions()));
            System.exit(1);
        }

        botName = dankConfig.getString("botName");
        oauth = dankConfig.getString("oauth");
        admin = dankConfig.getString("admin");
        trustedUsers = dankConfig.getStringArray("trustedUsers");
        channel = "#" + dankConfig.getString("channel");

        silentJoinLeave = dankConfig.getBoolean("silentJoinLeave");
        superCommands = dankConfig.getBoolean("superCommands");
        logOutput = dankConfig.getBoolean("logOutput", false);

        System.out.println("Dankbot starting!");
        System.out.println("Botname: " + dankConfig.getString("botName"));
        System.out.println("Channel: " + dankConfig.getString("channel"));
        System.out.println("Admin: " + dankConfig.getString("admin"));

        setName(botName);
        setVerbose(logOutput);
        setMessageDelay(666);

        String ip = "irc.chat.twitch.tv";
        int port = 80;

        try {
            Servers servers = new Gson().fromJson(DankUtils.readUrl("https://tmi.twitch.tv/servers?channel=".concat(channel.substring(1))), Servers.class);
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

        dankHandler = new DankHandler();
        dankWhisperBot = new DankWhisperBot(this, botName, oauth, admin, channel, superCommands, logOutput, dankHandler);

        dankHandler.addModule(new CommandAdd(this, dankWhisperBot, "!addcommand", 0, 0));
        dankHandler.addModule(new CommandRemove(this, dankWhisperBot, "!removecommand", 0 ,0));
        dankHandler.addModule(new Stop(this, dankWhisperBot, "!stop", 0, 0));
        
        if(!silentJoinLeave)
            channelMessage("/me joining MrDestructoid");
    }

    public void channelMessage(String message){
        sendMessage(channel, message);
    }

    public void onMessageWithTags(String channel, String sender, String login, String hostname, String message, HashMap<String, String> tags){
        dankHandler.checkChannelMessage(message, sender, tags);
    }

    public DankHandler getDankHandler(){
        return dankHandler;
    }

    public long getTimeStarted(){
        return timeStarted;
    }

    public String getAdmin() {
        return admin;
    }

    public DankConfig getDankConfig() {
        return dankConfig;
    }

    public boolean isSilentJoinLeave() {
        return silentJoinLeave;
    }
}
