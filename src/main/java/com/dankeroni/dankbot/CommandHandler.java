package com.dankeroni.dankbot;

import java.util.HashMap;

public class CommandHandler {

    private HashMap<String, Command> commands = new HashMap<>();

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        for (HashMap.Entry<String, Command> command : commands.entrySet()) {
            if (Utils.detectCommand(message, command.getKey())) {
                command.getValue().onChannelCommand();
                return true;
            }
        }
        return false;
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        for (HashMap.Entry<String, Command> command : commands.entrySet()) {
            if (Utils.detectCommand(message, command.getKey())) {
                command.getValue().onWhisperCommand(sender);
                return true;
            }
        }
        return false;
    }

    public void addCommand(String commandString, Command command) {
        commands.put(commandString, command);
    }

    public void removeCommand(String commandName) {
        commands.remove(commandName);
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
