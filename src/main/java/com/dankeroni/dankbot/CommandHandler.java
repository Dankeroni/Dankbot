package com.dankeroni.dankbot;

import java.util.HashMap;

public class CommandHandler {

    public ChannelBot channelBot;
    public HashMap<String, Command> commands = new HashMap<>();

    public CommandHandler(ChannelBot channelBot) {
        this.channelBot = channelBot;
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        for (HashMap.Entry<String, Command> command : commands.entrySet()) {
            if (Utils.detectCommand(message, command.getKey())) {
                channelBot.formattedChannelMessage(command.getValue().getResponse(), sender, message, tags);
                return true;
            }
        }
        return false;
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        for (HashMap.Entry<String, Command> command : commands.entrySet()) {
            if (Utils.detectCommand(message, command.getKey())) {
                channelBot.getWhisperBot().formattedWhisperMessage(command.getValue().getResponse(), sender, message, tags);
                return true;
            }
        }
        return false;
    }

    public boolean addCommand(String commandString, Command command) {
        if (commands.containsKey(commandString)) {
            return false;
        } else {
            commands.put(commandString, command);
            return true;
        }
    }

    public boolean removeCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            commands.remove(commandName);
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
