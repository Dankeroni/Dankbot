package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.DankChannelBot;
import com.dankeroni.dankbot.DankModule;
import com.dankeroni.dankbot.DankUtils;
import com.dankeroni.dankbot.DankWhisperBot;

public class CommandAdd extends DankModule{

    public CommandAdd(DankChannelBot dankChannelBot, DankWhisperBot dankWhisperBot, String command, int globalCooldown, int userCooldown) {
        super(dankChannelBot, dankWhisperBot, command, globalCooldown, userCooldown);
    }

    protected void onChannelCommand(String message, String sender){
        if(!sender.equalsIgnoreCase(dankChannelBot.getAdmin()))return;
        String [] words = DankUtils.splitString(message);
        dankChannelBot.getDankHandler().addModule(buildModule(words));
    }

    private DankModule buildModule(String[] words){
        String commandToUse = words[1].toLowerCase();
        if(!commandToUse.startsWith("!"))
            commandToUse = "!".concat(commandToUse);
        String moduleName = words[2].toLowerCase();
        int globalCooldown = Integer.parseInt(words[3]);
        int userCooldown = Integer.parseInt(words[4]);
        switch(moduleName){
            case "botuptime":
                return new BotUpTime(dankChannelBot, dankWhisperBot, commandToUse, globalCooldown, userCooldown);
            case "vanish":
                return new Vanish(dankChannelBot, dankWhisperBot, commandToUse, globalCooldown, userCooldown);
            case "time":
                return new Time(dankChannelBot, dankWhisperBot, commandToUse, globalCooldown, userCooldown);
        }
        return null;
    }
}
