package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.util.HashMap;

public class Commands extends Module {

    public HashMap<String, ActionCommand> actionCommands = new HashMap<>();
    public HashMap<String, MessageCommand> messageCommands = new HashMap<>(), customCommands = new HashMap<>();

    public Commands(ChannelBot channelBot) {
        super(channelBot);
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
        this.handleMessage(message, sender, tags, false);
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        this.handleMessage(message, sender, tags, true);
    }

    public void handleMessage(String message, String sender, HashMap<String, String> tags, boolean whisper) {
        String commandName = message.split(" ")[0].toLowerCase();
        if (actionCommands.containsKey(commandName)) {
            ActionCommand actionCommand = actionCommands.get(commandName);
            if (this.commandReady(actionCommand, sender) && Utils.checkAccessLevel(sender, actionCommand.getAccessLevel()) && this.userReady(actionCommand, sender)) {
                Action action;
                if ((action = actionCommand.getAction()) != null) action.accept(message, sender, tags);

                if (!Utils.checkAccessLevel(sender, AccessLevel.SUPERMODERATOR))
                    this.putUserOnCooldown(actionCommand, sender);
                this.putCommandOnCooldown(actionCommand);
            }
        }

        if (customCommands.containsKey(commandName) || messageCommands.containsKey(commandName)) {
            MessageCommand messageCommand = customCommands.get(commandName);
            messageCommand = messageCommands.getOrDefault(commandName, messageCommand);
            if (this.commandReady(messageCommand, sender) && Utils.checkAccessLevel(sender, messageCommand.getAccessLevel()) && this.userReady(messageCommand, sender)) {
                String unformattedMessage;
                if ((unformattedMessage = messageCommand.getMessage()) != null)
                    if (whisper) channelBot.formattedWhisperMessage(unformattedMessage, sender, message, tags);
                    else channelBot.formattedChannelMessage(unformattedMessage, sender, message, tags);

                if (!Utils.checkAccessLevel(sender, AccessLevel.SUPERMODERATOR))
                    this.putUserOnCooldown(messageCommand, sender);
                this.putCommandOnCooldown(messageCommand);
            }
        }
    }

    public boolean addActionCommand(ActionCommand command) {
        if (!containsCommand(command)) {
            actionCommands.put(command.getCommand().toLowerCase(), command);
            return true;
        } else return false;
    }

    public boolean addMessageCommand(MessageCommand command) {
        if (!containsCommand(command)) {
            messageCommands.put(command.getCommand().toLowerCase(), command);
            return true;
        } else return false;
    }

    public boolean addCustomCommand(MessageCommand command, boolean log) {
        if (!containsCommand(command)) {
            customCommands.put(command.getCommand().toLowerCase(), command);
            if (log) channelBot.log("Added custom command \"" + command.getCommand() + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public boolean removeCustomCommand(String commandName) {
        if (customCommands.containsKey(commandName.toLowerCase())) {
            customCommands.remove(commandName.toLowerCase());
            channelBot.log("Removed custom command \"" + commandName + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public boolean containsCommand(Command command) {
        return customCommands.containsKey(command.getCommand().toLowerCase()) || messageCommands.containsKey(command.getCommand().toLowerCase()) || actionCommands.containsKey(command.getCommand().toLowerCase());
    }

    public boolean commandReady(Command command, String user) {
        return !command.isOnGlobalCooldown() || Utils.checkAccessLevel(user, AccessLevel.SUPERMODERATOR);
    }

    public boolean userReady(Command command, String user) {
        return !command.getUsersOnCooldown().contains(user) || Utils.checkAccessLevel(user, AccessLevel.SUPERMODERATOR);
    }

    public void putCommandOnCooldown(Command command) {
        command.setOnGlobalCooldown(true);
        Utils.runDelayed(() -> command.setOnGlobalCooldown(false), command.getGlobalCooldown() * 1000);
    }

    public void putUserOnCooldown(Command command, String user) {
        command.getUsersOnCooldown().add(user);
        Utils.runDelayed(() -> command.getUsersOnCooldown().remove(user), command.getUserCooldown() * 1000);
    }

    public HashMap<String, ActionCommand> getActionCommands() {
        return actionCommands;
    }

    public HashMap<String, MessageCommand> getMessageCommands() {
        return messageCommands;
    }

    public HashMap<String, MessageCommand> getCustomCommands() {
        return customCommands;
    }
}
