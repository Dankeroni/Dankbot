package com.dankeroni.dankbot;

public abstract class DankModule{

    protected DankChannelBot dankChannelBot;
    protected DankWhisperBot dankWhisperBot;
    protected String command;
    protected int globalCooldown, userCooldown;

    public DankModule(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown){
        this.dankChannelBot = dankChannelBot;
        this.dankWhisperBot = dankWhisperBot;
        this.command = command;
        this.globalCooldown = globalCooldown;
        this.userCooldown = userCooldown;
    }

    protected void onChannelCommand(String message, String sender){}

    protected void onWhisperCommand(String message, String sender){}

    public String getCommand() {
        return command;
    }
}
