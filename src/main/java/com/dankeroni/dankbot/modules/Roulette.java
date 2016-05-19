package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.ActionCommand;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.TwitchTags;

import java.util.Random;

public class Roulette extends Module {

    public Random random = new Random();

    public Roulette(Bot bot) {
        super(bot);
        commands.addActionCommand(new ActionCommand("!roulette", this::roulette, AccessLevel.USER, 15, 0));
    }

    public void roulette(String message, String sender, TwitchTags tags) {
        Points points = bot.getPoints();
        int bet;
        try {
            bet = Integer.parseInt(Utils.makeArgs(message)[0]);
        } catch (NumberFormatException ignored) {
            return;
        }

        String displayName = tags.displayName;
        int userPoints = points.getPoints(sender);
        if (userPoints < bet) {
            bot.channelMessage(displayName + " you don't have enough points FeelsBadMan");
            return;
        }

        random.setSeed(System.nanoTime());
        if (random.nextInt(2) == 1) {
            points.addPoints(sender, bet);
            bot.channelMessage(displayName + " won " + bet + " points in roulette and now has " + (userPoints + bet) + " points PogChamp");
        } else {
            points.removePoints(sender, bet);
            bot.channelMessage(displayName + " lost " + bet + " points in roulette and now has " + (userPoints - bet) + " points 4Head");
        }
        points.savePoints();
    }
}
