package com.dankeroni.dankbot;

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
