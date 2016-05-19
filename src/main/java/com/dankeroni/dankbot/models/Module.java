package com.dankeroni.dankbot.models;

import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.modules.Commands;
import com.dankeroni.dankbot.modules.Users;

public abstract class Module {

    public Bot bot;
    public Commands commands;
    public Users users;

    public Module(Bot bot) {
        this.bot = bot;
        this.commands = bot.getCommands();
        this.users = bot.getUsers();
    }

    public void onChannelMessage(String message, String sender, TwitchTags tags) {
    }

    public void onWhisperMessage(String message, String sender, TwitchTags tags) {
    }
}
