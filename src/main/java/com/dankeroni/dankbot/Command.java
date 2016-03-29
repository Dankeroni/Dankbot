package com.dankeroni.dankbot;

public class Command {

    private ChannelBot channelBot;
    private String response;

    public Command(ChannelBot channelBot, String response) {
        this.channelBot = channelBot;
        this.response = response;
    }

    protected void onChannelCommand() {
        channelBot.channelMessage(response);
    }

    protected void onWhisperCommand(String sender) {
        channelBot.getWhisperBot().sendWhisper(sender, response);
    }

}
