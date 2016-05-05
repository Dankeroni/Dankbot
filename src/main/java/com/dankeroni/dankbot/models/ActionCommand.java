package com.dankeroni.dankbot.models;

import com.dankeroni.dankbot.AccessLevel;

public class ActionCommand extends Command {

    public Action action;

    public ActionCommand(String command, Action action, AccessLevel accessLevel, int globalCooldown, int userCooldown) {
        super(command, accessLevel, globalCooldown, userCooldown);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
