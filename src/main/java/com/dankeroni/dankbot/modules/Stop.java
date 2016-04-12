package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.util.HashMap;

public class Stop extends Module {

    public Stop(ChannelBot channelBot) {
        super(channelBot);
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return check(message, sender);
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return check(message, sender);
    }

    public boolean check(String message, String sender) {
        if (!Utils.detectCommand(message, "!stop")) return false;

        if (sender.equalsIgnoreCase(channelBot.getAdmin())) {
            this.stopBot();
        }

        return true;
    }

    public void stopBot() {
        channelBot.log("Dankbot stopping!", LogLevel.INFO);
        if (!channelBot.isSilentJoinLeave()) {
            String commitHash = channelBot.getCommitHash();
            int commitNumber = channelBot.getCommitNumber();

            if (!commitHash.trim().isEmpty() && !(commitNumber == 0)) {
                channelBot.channelMessage("/me commit " + commitHash + " number " + commitNumber + " leaving MrDestructoid");
            } else {
                channelBot.channelMessage("/me leaving MrDestructoid");
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channelBot.disconnect();
        channelBot.log(String.format("Log end: %s %s", Utils.date(), Utils.detailedTime()), LogLevel.DEBUG);
        channelBot.dispose();
        Runtime.getRuntime().halt(0);
    }
}
