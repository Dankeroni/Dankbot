package com.dankeroni.dankbot;

import java.util.HashMap;

public abstract class Module {

    public ChannelBot channelBot;

    public Module(ChannelBot channelBot) {
        this.channelBot = channelBot;
    }

    public abstract boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags);

    public abstract boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags);
}
