package com.dankeroni.dankbot;

import com.dankeroni.dankbot.modules.CommandAdd;
import com.dankeroni.dankbot.modules.CommandRemove;
import com.dankeroni.dankbot.modules.Stop;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DankChannelBot extends PircBot{

    public static void main(String[] args){
        try(FileReader reader = new FileReader("config.properties")){
            Properties properties = new Properties();
            properties.load(reader);
            String botName = properties.getProperty("botname");
            String oauth = properties.getProperty("oauth");
            String admin = properties.getProperty("admin");
            String channel = "#" + properties.getProperty("channel");
            boolean silentJoinLeave = Boolean.parseBoolean(properties.getProperty("silentJoinLeave"));
            boolean superCommands = Boolean.parseBoolean(properties.getProperty("superCommands"));
            boolean logOutput = Boolean.parseBoolean(properties.getProperty("logOutput"));
            if(botName.isEmpty() || oauth.isEmpty() || admin.isEmpty() || channel.isEmpty() || properties.getProperty("silentJoinLeave").isEmpty() || properties.getProperty("superCommands").isEmpty() || properties.getProperty("logOutput").isEmpty()) {
                System.out.println("Please fill in the config file!");
                return;
            }
            System.out.println("Dankbot starting!");
            System.out.println("Botname: " + botName);
            System.out.println("Channel: " + channel.substring(1));
            System.out.println("Admin: " + admin);
            new DankChannelBot(botName, oauth, admin, channel, silentJoinLeave, superCommands, logOutput);
        } catch(Exception e){
            System.out.println("Please fix your config file!");
            e.printStackTrace();
        }
    }

    private long timeStarted = System.currentTimeMillis();
    private String botName, oauth, admin, channel;
    private boolean silentJoinLeave, superCommands, logOutput, running = false;
    private DankHandler dankHandler;
    private DankWhisperBot dankWhisperBot;

    public DankChannelBot(String botName, String oauth, String admin, String channel, boolean silentJoinLeave, boolean superCommands, boolean logOutput){
        this.botName = botName;
        this.oauth = oauth;
        this.admin = admin;
        this.channel = channel;
        this.silentJoinLeave = silentJoinLeave;
        this.superCommands = superCommands;
        this.logOutput = logOutput;
        start();
    }

    private void start(){
        if(running) return;
        running = true;
        setName(botName);
        setVerbose(logOutput);
        setMessageDelay(666);
        try{
            connect("irc.twitch.tv", 6667, oauth);
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
    }

    public void channelMessage(String message){
        sendMessage(channel, message);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message){
        dankHandler.checkChannelMessage(message, sender);
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
}
