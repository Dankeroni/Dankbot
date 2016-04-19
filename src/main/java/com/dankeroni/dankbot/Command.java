package com.dankeroni.dankbot;

public class Command {

    public String command, response;
    public TriConsumer action;
    public AccessLevel accessLevel;
    public int globalCooldown, userCooldown;

    public Command(String command, TriConsumer action, String response, AccessLevel accessLevel, int globalCooldown, int userCooldown) {
        this.command = command;
        this.action = action;
        this.response = response;
        this.accessLevel = accessLevel;
        this.globalCooldown = globalCooldown;
        this.userCooldown = userCooldown;
    }

    public String getCommand() {
        return command;
    }

    public TriConsumer getAction() {
        return action;
    }

    public String getResponse() {
        return response;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public int getGlobalCooldown() {
        return globalCooldown;
    }

    public int getUserCooldown() {
        return userCooldown;
    }

}
