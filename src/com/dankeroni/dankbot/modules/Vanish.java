package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankWhisperBot;

import java.util.HashMap;

public class Vanish extends DankModule{

    public Vanish(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender, HashMap<String, String> tags){
        dankChannelBot.channelMessage(".timeout ".concat(sender).concat(" 1"));
    }
}
