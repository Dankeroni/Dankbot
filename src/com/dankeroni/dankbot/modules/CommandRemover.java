package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankUtils;
import com.dankeroni.dankbot.DankWhisperBot;

public class CommandRemover extends DankModule{

    public CommandRemover(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender){
        if(!sender.equalsIgnoreCase(dankChannelBot.getAdmin()))return;
        String[] words = DankUtils.splitString(message);
        for(DankModule dankModule: dankChannelBot.getDankHandler().getDankModules()){
            if(dankModule.getCommand().substring(1).equalsIgnoreCase(words[1])){
                dankChannelBot.getDankHandler().removeModule(dankModule);
                break;
            }
        }
    }
}
