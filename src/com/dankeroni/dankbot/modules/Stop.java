package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankWhisperBot;

public class Stop extends DankModule{

    public Stop(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender){
        if(!sender.equalsIgnoreCase(dankChannelBot.getAdmin()))return;
        stopBot();
    }

    private void stopBot() {
        dankChannelBot.disconnect();
        dankChannelBot.dispose();
        dankWhisperBot.disconnect();
        dankWhisperBot.dispose();
        System.exit(0);
    }
}
