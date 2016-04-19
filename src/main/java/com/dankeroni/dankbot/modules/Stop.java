package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class Stop extends Module {

    public Stop(ChannelBot channelBot) {
        super(channelBot);

        commands.addCommand(new Command("!stop", this::stop, null, AccessLevel.ADMIN, 5, 5), false);
    }

    public void stop() {
        this.stop(null, null, null);
    }

    public void stop(String message, String sender, HashMap<String, String> tags) {
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
    }
}
