package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class Commands extends Module {

    public HashMap<String, Command> commands = new HashMap<>();

    public Commands(ChannelBot channelBot) {
        super(channelBot);
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
        String commandName = message.split(" ")[0];
        if (commands.containsKey(commandName)) {
            Command command = commands.get(commandName);
            TriConsumer action;
            String response;
            if ((action = command.getAction()) != null) action.accept(message, sender, tags);
            if ((response = command.getResponse()) != null)
                channelBot.formattedChannelMessage(response, sender, message, tags);
        }
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        String commandName = message.split(" ")[0];
        if (commands.containsKey(commandName)) {
            Command command = commands.get(commandName);
            TriConsumer action;
            String response;
            if ((action = command.getAction()) != null) action.accept(message, sender, tags);
            if ((response = command.getResponse()) != null)
                channelBot.formattedWhisperMessage(response, sender, message, tags);
        }
    }

    public boolean addCommand(Command command, boolean log) {
        if (!commands.containsKey(command.getCommand())) {
            commands.put(command.getCommand(), command);
            if (log) channelBot.log("Added command \"" + command.getCommand() + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public boolean removeCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            commands.remove(commandName);
            channelBot.log("Removed command \"" + commandName + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
