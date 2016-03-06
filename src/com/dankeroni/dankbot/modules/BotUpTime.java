package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankWhisperBot;

import java.util.concurrent.TimeUnit;

public class BotUpTime extends DankModule{

    public BotUpTime(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    public void onChannelCommand(String  message, String sender){
        dankChannelBot.channelMessage("The bot has been up for: " + getBotUpTime());
    }

    private String getBotUpTime() {
        long millis = System.currentTimeMillis() - dankChannelBot.getTimeStarted();
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return String.valueOf(days) +
                " Days " +
                hours +
                " Hours " +
                minutes +
                " Minutes " +
                seconds +
                " Seconds";
    }
}
