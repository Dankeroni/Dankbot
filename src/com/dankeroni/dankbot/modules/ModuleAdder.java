package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankUtils;
import com.dankeroni.dankbot.DankWhisperBot;

public class ModuleAdder extends DankModule{

    public ModuleAdder(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender){
        String [] words = DankUtils.splitString(message);
        dankChannelBot.getDankHandler().addModule(buildModule(words));
    }

    private DankModule buildModule(String[] words){
        String command = ("!" + words[1]).toLowerCase();
        String moduleName = words[2].toLowerCase();
        int globalCooldown = Integer.parseInt(words[3]);
        int userCooldown = Integer.parseInt(words[4]);
        switch(moduleName){
            case "botuptime":
                return new BotUpTime(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
        }
        return null;
    }
}
