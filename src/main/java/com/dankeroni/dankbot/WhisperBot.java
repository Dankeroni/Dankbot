package com.dankeroni.dankbot;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.util.HashMap;

public class WhisperBot extends PircBot {

    private ChannelBot channelBot;
    private String botName, oauth, admin, channel;
    private boolean superCommand, twitchChat, running = false;
    private ModuleHandler moduleHandler;

    public WhisperBot(ChannelBot channelBot, String botName, String oauth, String admin, String channel, boolean superCommands, boolean twitchChat, ModuleHandler moduleHandler) {
        this.channelBot = channelBot;
        this.botName = botName;
        this.oauth = oauth;
        this.admin = admin;
        this.channel = channel;
        this.superCommand = superCommands;
        this.twitchChat = twitchChat;
        this.moduleHandler = moduleHandler;
        start();
    }

    private void start(){
        if(running)
            return;

        running = true;
        setName(botName);
        setVerbose(twitchChat);

        String ip = "192.16.64.180";
        int port = 443;

        //TODO: Fix whisperbot
        /*try {
            Servers groupServers = new Gson().fromJson(Utils.readUrl("https://tmi.twitch.tv/servers?channel=group"), Servers.class);
            String[] ipAndPort = groupServers.randomServer().split(":");
            ip = ipAndPort[0];
            port = Integer.parseInt(ipAndPort[1]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem fetching the whisper server list, using default server");
        }*/

        int tries = 0;

        while (!isConnected() && tries++ < 5) {
            try {
                connect(ip, port, oauth);
                Thread.sleep(500);
            } catch (IOException | IrcException e) {
                e.printStackTrace();
                System.out.println("Twitch whisper servers offline/Check your internet connection/firewall");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isConnected()) channelBot.log("Whisper servers down!");

        sendRawLineViaQueue("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");
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
}
