package com.dankeroni.dankbot;

import java.util.HashMap;

public abstract class Module {

    protected ChannelBot channelBot;

    public Module(ChannelBot channelBot) {
        this.channelBot = channelBot;
    }

    protected abstract boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags);

    protected abstract boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags);
}
