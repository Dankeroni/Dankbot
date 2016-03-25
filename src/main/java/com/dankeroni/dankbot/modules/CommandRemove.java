package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankUtils;
import com.dankeroni.dankbot.DankWhisperBot;

import java.util.HashMap;

public class CommandRemove extends DankModule{

    public CommandRemove(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender, HashMap<String, String> tags){
        if(!sender.equalsIgnoreCase(dankChannelBot.getAdmin()))
            return;

        String[] words = DankUtils.splitString(message);

        for(DankModule dankModule: dankChannelBot.getDankHandler().getDankModules()){
            if(dankModule.getCommand().substring(1).equalsIgnoreCase(words[1])){
                dankChannelBot.getDankHandler().removeModule(dankModule);
                break;
            }
        }
    }
}
