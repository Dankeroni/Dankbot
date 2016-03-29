package com.dankeroni.dankbot;

import java.util.HashMap;

public class Command {

    private ChannelBot channelBot;
    private String response;

    public Command(ChannelBot channelBot, String response) {
        this.channelBot = channelBot;
        this.response = response;
    }

    protected void onChannelCommand(String message, HashMap<String, String> tags) {
        channelBot.channelMessage(Utils.format(response, message, tags));
    }

    protected void onWhisperCommand(String message, String sender, HashMap<String, String> tags) {
        channelBot.getWhisperBot().sendWhisper(sender, Utils.format(response, message, tags));
    }

}
