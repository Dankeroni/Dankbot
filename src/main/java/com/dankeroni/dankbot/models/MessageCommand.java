package com.dankeroni.dankbot.models;

import com.dankeroni.dankbot.AccessLevel;

public class MessageCommand extends Command {

    public String message;

    public MessageCommand(String command, String message, AccessLevel accessLevel, int globalCooldown, int userCooldown) {
        super(command, accessLevel, globalCooldown, userCooldown);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
