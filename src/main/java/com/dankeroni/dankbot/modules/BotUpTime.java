package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BotUpTime extends Module {

    public BotUpTime(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        if (Utils.detectCommand(message, "!up")) {
            this.onChannelCommand(tags);
            return true;

        } else {
            return false;
        }
    }

    protected boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        if (Utils.detectCommand(message, "!up")) {
            this.onWhisperCommand(sender);
            return true;

        } else {
            return false;
        }
    }

    private void onChannelCommand(HashMap<String, String> tags) {
        channelBot.channelMessage("The bot has been up for: " + this.getBotUpTime());
    }

    private void onWhisperCommand(String sender) {
        channelBot.getWhisperBot().sendWhisper(sender, "The bots has been up for: " + this.getBotUpTime());
    }

    private String getBotUpTime() {
        long millis = System.currentTimeMillis() - channelBot.getTimeStarted();
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        if (days > 0) {
            return String.format("%s Days %s Hours %s Minutes %s Seconds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%s Hours %s Minutes %s Seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%s Minutes %s Seconds", minutes, seconds);
        } else {
            return String.format("%s Seconds", seconds);
        }
    }
}
