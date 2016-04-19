package com.dankeroni.dankbot;

import com.dankeroni.dankbot.modules.Commands;

import java.util.HashMap;

public abstract class Module {

    public ChannelBot channelBot;
    public Commands commands;

    public Module(ChannelBot channelBot) {
        this.channelBot = channelBot;
        this.commands = channelBot.getCommands();
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
    }
}
