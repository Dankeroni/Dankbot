package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.Servers;
import com.google.gson.Gson;
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

        String ip = "irc.chat.twitch.tv";
        int port = 443;

        try {
            Servers servers = new Gson().fromJson(DankUtils.readUrl("https://tmi.twitch.tv/servers?channel=group"), Servers.class);
            String[] ipAndPort = servers.randomServer().split(":");
            ip = ipAndPort[0];
            port = Integer.parseInt(ipAndPort[1]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem fetching the whisper server list, using default server");
        }

        try{
            connect(ip, port, oauth);
        }catch(IOException | IrcException e){
            e.printStackTrace();
            System.out.println("Twitch whisper servers offline/Check your internet connection/firewall");
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
