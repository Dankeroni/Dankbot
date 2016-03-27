package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class CustomCommands extends Module {

    private CommandHandler commandHandler = new CommandHandler();

    public CustomCommands(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String user, HashMap<String, String> tags) {
        return commandHandler.checkChannelMessage(message, user, tags) || check(message, user);
    }

    protected boolean checkWhisperMessage(String message, String user, HashMap<String, String> tags) {
        return commandHandler.checkWhisperMessage(message, user, tags) || check(message, user);
    }

    private boolean check(String message, String user) {
        if (Utils.detectCommand(message, "!addcom")) {
            this.addCustomCommand(user, message);
            return true;

        } else if (Utils.detectCommand(message, "!removecom")) {
            this.removeCustomCommand(user, message);
            return true;

        } else {
            return false;
        }
    }

    private void addCustomCommand(String user, String message) {
        if (!user.equalsIgnoreCase(channelBot.getAdmin())) return;

        String[] words = message.split(" ", 3);
        String commandName = words[1].toLowerCase();
        String response = words[2];

        commandHandler.addCommand(commandName, new Command(channelBot, response));
    }

    private void removeCustomCommand(String user, String message) {
        if (!user.equalsIgnoreCase(channelBot.getAdmin())) return;

        commandHandler.removeCommand(message.split(" ")[1]);
    }
}
