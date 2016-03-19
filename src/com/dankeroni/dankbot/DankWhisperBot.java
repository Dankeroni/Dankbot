package com.dankeroni.dankbot;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.util.HashMap;

public class DankWhisperBot extends PircBot{

    private DankChannelBot dankChannelBot;
    private String botName, oauth, admin, channel;
    private boolean superCommand, logOutput, running = false;
    private DankHandler dankHandler;

    public DankWhisperBot(DankChannelBot dankChannelBot, String botName, String oauth, String admin, String channel, boolean superCommands, boolean logOutput, DankHandler dankHandler){
        this.dankChannelBot = dankChannelBot;
        this.botName = botName;
        this.oauth = oauth;
        this.admin = admin;
        this.channel = channel;
        this.superCommand = superCommands;
        this.logOutput = logOutput;
        this.dankHandler = dankHandler;
        start();
    }

    private void start(){
        if(running)
            return;

        running = true;
        setName(botName);
        setVerbose(logOutput);

        try{
            connect("192.16.64.180", 443, oauth);
        }catch(IOException | IrcException e){
            System.out.println("Twitch whisper servers offline/Check your internet connection/firewall");
            e.printStackTrace();
            return;
        }

        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");
    }

    public void sendWhisper(String user, String message) {
        sendMessage("#dankeroni", String.format(".w %s %s", user, message));
    }

    public void onWhisperWithTags(String sender, String login, String hostname, String message, HashMap<String, String> tags){
        dankHandler.checkWhisperMessage(message, sender, tags);
   }
}
