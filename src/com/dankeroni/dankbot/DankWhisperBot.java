package com.dankeroni.dankbot;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class DankWhisperBot extends PircBot{

    private DankChannelBot dankChannelBot;
    private String botName, oauth, admin, channel;
    private boolean superCommand, logOutput;
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
        setName(botName);
        setVerbose(logOutput);
        try{
            connect("199.9.253.58", 443, oauth);
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

    public void onWhisper(String sender, String login, String hostname, String message){
      dankHandler.checkWhisperMessage(message, sender);
   }
}
