package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankWhisperBot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Time extends DankModule{

    public Time(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender, HashMap<String, String> tags){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        dankChannelBot.channelMessage("UTC time: ".concat(sdf.format(new Date())));
    }
}
