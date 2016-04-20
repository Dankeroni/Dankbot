package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;

public class Raffle extends Module {

    public boolean raffleRunning = false;
    public ArrayList<String> enteredUsers;
    public Random random = new Random();

    public BiConsumer raffle = (rafflePoints, raffleTime) -> {
        raffleRunning = true;
        enteredUsers = new ArrayList<>();
        channelBot.channelMessage("A raffle has begun for " + rafflePoints + " points. Type !join to join the raffle! The raffle will end in " + raffleTime + " seconds");
        try {
            Thread.sleep((long) ((int) raffleTime * 1000 * 0.25));
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) ((int) raffleTime * 0.75) + " seconds! Type !join to join the raffle!");
            Thread.sleep((long) ((int) raffleTime * 1000 * 0.25));
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) ((int) raffleTime * 0.50) + " seconds! Type !join to join the raffle!");
            Thread.sleep((long) ((int) raffleTime * 1000 * 0.25));
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) ((int) raffleTime * 0.25) + " seconds! Type !join to join the raffle!");
            Thread.sleep((long) ((int) raffleTime * 1000 * 0.25));
        } catch (InterruptedException e) {
        }
        String winner = getWinner();
        if (winner != null) {
            channelBot.channelMessage("The raffle has finished! " + winner + " won " + rafflePoints + " points! Kappa");
        } else {
            channelBot.channelMessage("Nobody entered the raffle.");
        }
        raffleRunning = false;
    };

    public Raffle(ChannelBot channelBot) {
        super(channelBot);

        commands.addCommand(new Command("!raffle", this::startRaffle, null, AccessLevel.MODERATOR, 0, 0), false);
        commands.addCommand(new Command("!join", this::join, null, AccessLevel.USER, 0, 2), false);
    }

    public void startRaffle(String message, String sender, HashMap<String, String> tags) {
        if (raffleRunning) return;

        String[] messageArgs = Utils.makeArgs(message);
        int points, time;

        try {
            points = Integer.parseInt(messageArgs[0]);
            time = Integer.parseInt(messageArgs[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        Thread raffleThread = new Thread(() -> {
            raffle.accept(points, time);
        });
        raffleThread.setDaemon(true);
        raffleThread.start();
    }

    public String getWinner() {
        if (enteredUsers.size() == 0) return null;
        else return enteredUsers.get(random.nextInt(enteredUsers.size()));
    }

    public void join(String message, String sender, HashMap<String, String> tags) {
        if (raffleRunning && !enteredUsers.contains(sender))
            enteredUsers.add(tags.get("display-name"));
    }
}
