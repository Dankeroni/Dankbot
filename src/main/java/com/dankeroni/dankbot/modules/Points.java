package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Points extends Module {

    public HashMap<String, Integer> points = new HashMap<>();
    public File pointsFile = new File(channelBot.getPath() + "save.points");
    public boolean pointsFileExists;

    public Points(ChannelBot channelBot) {
        super(channelBot);

        if (pointsFile.exists()) {
            pointsFileExists = true;
            channelBot.log("Loading points", LogLevel.DEBUG);
            try (BufferedReader br = new BufferedReader(new FileReader(pointsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] nameAndPoints = line.trim().split(" ");
                    points.put(nameAndPoints[0], Integer.parseInt(nameAndPoints[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                channelBot.log("Points file not found, generating new one", LogLevel.INFO);
                pointsFileExists = pointsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                channelBot.log("Unable to generate points file, points will not be saved until you fix the problem and restart the bot!", LogLevel.WARN);
                pointsFileExists = false;
            }
        }

        Utils.runDelayed(this::pointsForChatting, 300000);

        commands.addActionCommand(new ActionCommand("!points", this::userPointsWhisper, AccessLevel.USER, 4, 10));
        commands.addActionCommand(new ActionCommand("!userpoints", this::userPoints, AccessLevel.USER, 4, 10));
    }


    public void pointsForChatting() {
        for (ArrayList<String> chattersList : Utils.getAllChattersLists())
            for (String chatter : chattersList)
                points.put(chatter.toLowerCase(), points.getOrDefault(chatter.toLowerCase(), 0) + 10);
        this.savePoints();
        Utils.runDelayed(this::pointsForChatting, 300000);
    }

    public void userPoints(String message, String sender, HashMap<String, String> tags) {
        channelBot.channelMessage(tags.get("display-name") + " has " + points.getOrDefault(sender, 0) + " points");
    }

    public void userPointsWhisper(String message, String sender, HashMap<String, String> tags) {
        channelBot.whisperMessage(sender, "You have " + points.getOrDefault(sender, 0) + " points");
    }

    public synchronized void savePoints() {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(pointsFile));
            for (HashMap.Entry<String, Integer> userAndPoints : points.entrySet())
                printWriter.println(userAndPoints.getKey() + " " + userAndPoints.getValue());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            channelBot.log("Error saving points", LogLevel.WARN);
        }
    }
}
