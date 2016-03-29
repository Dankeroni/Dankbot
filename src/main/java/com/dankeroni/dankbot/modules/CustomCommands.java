package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class CustomCommands extends Module {

    private CommandHandler commandHandler = new CommandHandler();

    public CustomCommands(ChannelBot channelBot) {
        super(channelBot);
    }

    protected boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return commandHandler.checkChannelMessage(message, sender, tags) || check(message, sender);
    }

    protected boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return commandHandler.checkWhisperMessage(message, sender, tags) || check(message, sender);
    }

    private boolean check(String message, String sender) {
        if (Utils.detectCommand(message, "!addcom")) {
            this.addCustomCommand(sender, message);
            return true;

        } else if (Utils.detectCommand(message, "!removecom")) {
            this.removeCustomCommand(sender, message);
            return true;

        } else {
            return false;
        }
    }

    private void addCustomCommand(String sender, String message) {
        if (!sender.equalsIgnoreCase(channelBot.getAdmin())) return;

        String[] words = message.split(" ", 3);
        String commandName = words[1].toLowerCase();
        String response = words[2];

        commandHandler.addCommand(commandName, new Command(channelBot, response));
    }

    private void removeCustomCommand(String sender, String message) {
        if (!sender.equalsIgnoreCase(channelBot.getAdmin())) return;

        commandHandler.removeCommand(message.split(" ")[1]);
    }
}
