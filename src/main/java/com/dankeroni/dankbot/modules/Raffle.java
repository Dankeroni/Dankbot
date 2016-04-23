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

    public BiConsumer<Integer, Integer> raffle = (rafflePoints, raffleTime) -> {
        raffleRunning = true;
        enteredUsers = new ArrayList<>();
        channelBot.channelMessage("A raffle has begun for " + rafflePoints + " points. Type !join to join the raffle! The raffle will end in " + raffleTime + " seconds");
        long sleepTime = (long) (raffleTime * 1000 * 0.25);
        try {
            Thread.sleep(sleepTime);
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.75) + " seconds! Type !join to join the raffle!");
            Thread.sleep(sleepTime);
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.50) + " seconds! Type !join to join the raffle!");
            Thread.sleep(sleepTime);
            channelBot.channelMessage("The raffle for " + rafflePoints + " points ends in " + (int) (raffleTime * 0.25) + " seconds! Type !join to join the raffle!");
            Thread.sleep(sleepTime);
        } catch (InterruptedException ignored) {
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

        commands.addActionCommand(new ActionCommand("!raffle", this::startRaffle, AccessLevel.MODERATOR, 0, 0));
        commands.addActionCommand(new ActionCommand("!join", this::join, AccessLevel.USER, 0, 2));
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
