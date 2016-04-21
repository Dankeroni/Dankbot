package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class Stop extends Module {

    public Stop(ChannelBot channelBot) {
        super(channelBot);

        commands.addActionCommand(new ActionCommand("!stop", this::stop, AccessLevel.ADMIN, 5, 5));
    }

    public void stop() {
        this.stop(null, null, null);
    }

    public void stop(String message, String sender, HashMap<String, String> tags) {
        if (!channelBot.isRunning()) return;

        channelBot.log("Dankbot stopping!", LogLevel.INFO);
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

        channelBot.disconnect();
        channelBot.dispose();
        channelBot.log(String.format("Log end: %s %s", Utils.date(), Utils.detailedTime()), LogLevel.DEBUG);
        channelBot.setRunning(false);
    }
}
