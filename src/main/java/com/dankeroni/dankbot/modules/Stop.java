package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.util.HashMap;

public class Stop extends Module {

    public Stop(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String user, HashMap<String, String> tags) {
        return check(message, user);
    }

    protected boolean checkWhisperMessage(String message, String user, HashMap<String, String> tags) {
        return check(message, user);
    }

    protected boolean check(String message, String user) {
        if (!Utils.detectCommand(message, "!stop")) return false;

        if (user.equalsIgnoreCase(channelBot.getAdmin())) {
            this.stopBot();
        }

        return true;
    }

    private void stopBot() {
        if (!channelBot.isSilentJoinLeave()) {
            channelBot.channelMessage("/me leaving MrDestructoid");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channelBot.disconnect();
        channelBot.dispose();
        channelBot.getWhisperBot().disconnect();
        channelBot.getWhisperBot().dispose();
    }
}
