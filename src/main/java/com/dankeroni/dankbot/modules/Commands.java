package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.*;

import java.util.HashMap;

public class Commands extends Module {

    public HashMap<String, ActionCommand> actionCommands = new HashMap<>();
    public HashMap<String, MessageCommand> messageCommands = new HashMap<>(), customCommands = new HashMap<>();

    public Commands(Bot bot) throws NullPointerException {
        super(bot);
    }

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
        this.handleMessage(message, sender, tags, false);
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        this.handleMessage(message, sender, tags, true);
    }

    public void handleMessage(String message, String sender, HashMap<String, String> tags, boolean whisper) {
        users = bot.getUsers();
        String commandName = message.split(" ")[0].toLowerCase();
        if (actionCommands.containsKey(commandName)) {
            ActionCommand actionCommand = actionCommands.get(commandName);
            if (this.commandReady(actionCommand, sender) && users.checkAccessLevel(sender, actionCommand.getAccessLevel()) && this.userReady(actionCommand, sender)) {
                Action action;
                if ((action = actionCommand.getAction()) != null) action.accept(message, sender, tags);

                if (!users.checkAccessLevel(sender, AccessLevel.SUPERMOD))
                    this.putUserOnCooldown(actionCommand, sender);
                this.putCommandOnCooldown(actionCommand);
            }
        }

        if (customCommands.containsKey(commandName) || messageCommands.containsKey(commandName)) {
            MessageCommand messageCommand = customCommands.get(commandName);
            messageCommand = messageCommands.getOrDefault(commandName, messageCommand);
            if (this.commandReady(messageCommand, sender) && users.checkAccessLevel(sender, messageCommand.getAccessLevel()) && this.userReady(messageCommand, sender)) {
                String unformattedMessage;
                if ((unformattedMessage = messageCommand.getMessage()) != null)
                    if (whisper) bot.formattedWhisperMessage(unformattedMessage, sender, message, tags);
                    else bot.formattedChannelMessage(unformattedMessage, sender, message, tags);

                if (!users.checkAccessLevel(sender, AccessLevel.SUPERMOD))
                    this.putUserOnCooldown(messageCommand, sender);
                this.putCommandOnCooldown(messageCommand);
            }
        }
    }

    public boolean addActionCommand(ActionCommand command) {
        if (doesNotContainCommand(command)) {
            actionCommands.put(command.getCommand().toLowerCase(), command);
            return true;
        } else return false;
    }

    public boolean addMessageCommand(MessageCommand command) {
        if (doesNotContainCommand(command)) {
            messageCommands.put(command.getCommand().toLowerCase(), command);
            return true;
        } else return false;
    }

    public boolean addCustomCommand(MessageCommand command, boolean log) {
        if (doesNotContainCommand(command)) {
            customCommands.put(command.getCommand().toLowerCase(), command);
            if (log) bot.log("Added custom command \"" + command.getCommand() + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public boolean removeCustomCommand(String commandName) {
        if (customCommands.containsKey(commandName.toLowerCase())) {
            customCommands.remove(commandName.toLowerCase());
            bot.log("Removed custom command \"" + commandName + "\"", LogLevel.DEBUG);
            return true;
        } else return false;
    }

    public boolean doesNotContainCommand(Command command) {
        return !customCommands.containsKey(command.getCommand().toLowerCase()) && !messageCommands.containsKey(command.getCommand().toLowerCase()) && !actionCommands.containsKey(command.getCommand().toLowerCase());
    }

    public boolean commandReady(Command command, String user) {
        users = bot.getUsers();
        return !command.isOnGlobalCooldown() || users.checkAccessLevel(user, AccessLevel.SUPERMOD);
    }

    public boolean userReady(Command command, String user) {
        users = bot.getUsers();
        return !command.getUsersOnCooldown().contains(user) || users.checkAccessLevel(user, AccessLevel.SUPERMOD);
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
