package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Time extends Module {

    public Time(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String user, HashMap<String, String> tags) {
        if (Utils.detectCommand(message, "!time")) {
            channelBot.channelMessage(response());
            return true;
        } else {
            return false;
        }
    }

    protected boolean checkWhisperMessage(String message, String user, HashMap<String, String> tags) {
        if (Utils.detectCommand(message, "!time")) {
            channelBot.getWhisperBot().sendWhisper(user, response());
            return true;
        } else {
            return false;
        }
    }

    private String response() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return String.format("UTC time: %s", sdf.format(new Date()));
    }
}
