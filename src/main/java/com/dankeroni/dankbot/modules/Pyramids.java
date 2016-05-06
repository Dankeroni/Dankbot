package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.models.Module;

import java.util.HashMap;

public class Pyramids extends Module {

    public String pyramidWord = "";
    public int length = 0, maxLength = 0;
    public boolean countingUp = true;

    public Pyramids(Bot bot) {
        super(bot);
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
        String[] messageParts = message.split(" ");
        String messageWord = messageParts[0];

        if (!messageWord.equals(pyramidWord) || (countingUp && messageParts.length == 1)) {
            this.resetPyramid();
            if (messageParts.length == 1) {
                pyramidWord = messageWord;
                length++;
                maxLength++;
                countingUp = true;
            }
            return;
        }

        int tempCount = 0;
        for (String messagePart : messageParts)
            if (messagePart.equals(pyramidWord))
                tempCount++;
            else {
                this.resetPyramid();
                return;
            }

        if (countingUp && tempCount == length + 1) {
            length++;
            maxLength++;
            return;
        } else if (tempCount == length - 1) {
            length--;
            countingUp = false;
            if (length != 1) return;
        } else {
            this.resetPyramid();
            return;
        }

        switch (maxLength) {
            case 2:
                bot.channelMessage(tags.get("display-name") + ", 2-wide pyramids ResidentSleeper");
                break;

            case 3:
                bot.channelMessage(tags.get("display-name") + ", nice pyramid bro EleGiggle");
                break;

            case 4:
                bot.channelMessage(tags.get("display-name") + ", not bad");
                break;

            case 5:
                bot.channelMessage(tags.get("display-name") + ", nice pyramid PogChamp");
                break;

            default:
                bot.channelMessage(tags.get("display-name") + ", " + maxLength + "-wide PogChamp");
                break;
        }
        this.resetPyramid();
    }

    public void resetPyramid() {
        pyramidWord = "";
        length = 0;
        maxLength = 0;
        countingUp = true;
    }
}
