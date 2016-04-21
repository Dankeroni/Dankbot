package com.dankeroni.dankbot;

import java.util.ArrayList;

public class Command {

    public String command;
    public AccessLevel accessLevel;
    public int globalCooldown, userCooldown;
    public boolean onGlobalCooldown = false;
    public ArrayList<String> usersOnCooldown = new ArrayList<>();

    public Command(String command, AccessLevel accessLevel, int globalCooldown, int userCooldown) {
        this.command = command;
        this.accessLevel = accessLevel;
        this.globalCooldown = globalCooldown;
        this.userCooldown = userCooldown;
    }

    public String getCommand() {
        return command;
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

    public boolean isOnGlobalCooldown() {
        return onGlobalCooldown;
    }

    public void setOnGlobalCooldown(boolean onGlobalCooldown) {
        this.onGlobalCooldown = onGlobalCooldown;
    }

    public ArrayList<String> getUsersOnCooldown() {
        return usersOnCooldown;
    }
}

