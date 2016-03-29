package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.util.HashMap;

public class Vanish extends Module {

    public Vanish(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        if (Utils.detectCommand(message, "!vanish")) {
            channelBot.channelMessage(".timeout " + sender + " 1");
            return true;
        } else {
            return false;
        }
    }

    protected boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return false;
    }
}
