package com.dankeroni.dankbot;

import java.util.HashMap;

public abstract class Module {

    protected ChannelBot channelBot;
    protected String command;

    public Module(ChannelBot channelBot) {
        this.channelBot = channelBot;
    }

    protected abstract boolean checkChannelMessage(String message, String user, HashMap<String, String> tags);

    protected abstract boolean checkWhisperMessage(String message, String user, HashMap<String, String> tags);
}
