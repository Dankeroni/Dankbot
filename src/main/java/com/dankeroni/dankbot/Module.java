package com.dankeroni.dankbot;

import com.dankeroni.dankbot.modules.Commands;
import com.dankeroni.dankbot.modules.Users;

import java.util.HashMap;

public abstract class Module {

    public ChannelBot channelBot;
    public Commands commands;
    public Users userManager;

    public Module(ChannelBot channelBot) {
        this.channelBot = channelBot;
        this.commands = channelBot.getCommands();
        this.userManager = channelBot.getUsers();
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
    }
}
