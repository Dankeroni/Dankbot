package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.ActionCommand;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.TwitchTags;

import java.sql.SQLException;

public class Stop extends Module {

    public Stop(Bot bot) {
        super(bot);

        commands.addActionCommand(new ActionCommand("!stop", this::stop, AccessLevel.ADMIN, 5, 5));
    }

    public void stop() {
        this.stop(null, null, null);
    }

    public void stop(String message, String sender, TwitchTags tags) {
        if (!bot.isRunning()) return;

        bot.log("Dankbot stopping!", LogLevel.INFO);
        String commitHash = bot.getCommitHash();
        int commitNumber = bot.getCommitNumber();

        if (!commitHash.trim().isEmpty() && !(commitNumber == 0)) {
            bot.channelMessage("/me commit " + commitHash + " number " + commitNumber + " leaving MrDestructoid");
        } else {
            bot.channelMessage("/me leaving MrDestructoid");
        }

        Utils.runDelayed(() -> {
            bot.disconnect();
            bot.dispose();
            try {
                bot.getDatabase().getConnection().close();
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
            }
            spark.Spark.stop();
            bot.log(String.format("Log end: %s %s", Utils.date(), Utils.detailedTime()), LogLevel.DEBUG);
            bot.setRunning(false);
        }, 500, false);
    }
}
