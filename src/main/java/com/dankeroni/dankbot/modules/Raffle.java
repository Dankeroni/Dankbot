package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.ActionCommand;
import com.dankeroni.dankbot.models.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Raffle extends Module {

    public boolean raffleRunning = false;
    public ArrayList<String> enteredUsers;
    public Random random = new Random();

    public Raffle(Bot bot) {
        super(bot);

        commands.addActionCommand(new ActionCommand("!raffle", this::startRaffle, AccessLevel.MOD, 0, 0));
        commands.addActionCommand(new ActionCommand("!join", this::join, AccessLevel.USER, 0, 2));
    }

    public void startRaffle(String message, String sender, HashMap<String, String> tags) {
        if (raffleRunning) return;

        String[] messageArgs = Utils.makeArgs(message);
        int rafflePoints, raffleTime;

        try {
            rafflePoints = Integer.parseInt(messageArgs[0]);
            raffleTime = Integer.parseInt(messageArgs[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        raffleRunning = true;
        enteredUsers = new ArrayList<>();
        bot.channelMessage("A raffle has begun for " + rafflePoints + " points. Type !join to join the raffle! The raffle will end in " + raffleTime + " seconds");
        long sleepTime = (long) (raffleTime * 1000 * 0.25);
        Utils.runDelayed(() -> bot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.75) + " seconds! Type !join to join the raffle!"), sleepTime);
        Utils.runDelayed(() -> bot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.50) + " seconds! Type !join to join the raffle!"), sleepTime * 2);
        Utils.runDelayed(() -> bot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.25) + " seconds! Type !join to join the raffle!"), sleepTime * 3);
        Utils.runDelayed(() -> {
            if (enteredUsers.size() > 0) {
                String winner = enteredUsers.get(random.nextInt(enteredUsers.size()));
                bot.getPoints().addPoints(winner, rafflePoints);
                bot.channelMessage("The raffle has finished! " + winner + " won " + rafflePoints + " points! Kappa");
            } else bot.channelMessage("Nobody entered the raffle.");
            raffleRunning = false;
        }, sleepTime * 4);
    }

    public void join(String message, String sender, HashMap<String, String> tags) {
        if (raffleRunning && !enteredUsers.contains(sender))
            enteredUsers.add(tags.get("display-name"));
    }
}
