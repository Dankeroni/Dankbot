package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankWhisperBot;

import java.util.HashMap;

public class Stop extends DankModule{

    public Stop(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender, HashMap<String, String> tags){
        if(!sender.equalsIgnoreCase(dankChannelBot.getAdmin()))return;
        stopBot();
    }

    private void stopBot() {
        if(!dankChannelBot.isSilentJoinLeave()) {
            dankChannelBot.channelMessage("/me leaving MrDestructoid");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        dankChannelBot.disconnect();
        dankChannelBot.dispose();
        dankWhisperBot.disconnect();
        dankWhisperBot.dispose();
        System.exit(0);
    }
}
