package com.dankeroni.dankbot;

import java.util.HashMap;
import java.util.LinkedList;

public class DankHandler {

    public LinkedList<DankModule> dankModules = new LinkedList<>();

    public void checkChannelMessage(String message, String sender, HashMap<String, String> tags){
        for(DankModule dankModule : dankModules){
            if(message.toLowerCase().startsWith(dankModule.getCommand())) {
                dankModule.onChannelCommand(message, sender, tags);
                break;
            }
        }
    }

    public void checkWhisperMessage(String message, String sender, HashMap<String, String> tags){
        for(DankModule dankModule : dankModules){
            if(message.toLowerCase().startsWith(dankModule.getCommand())){
                dankModule.onWhisperCommand(message, sender, tags);
                break;
            }
        }
    }

    public void addModule(DankModule dankModule){
        dankModules.add(dankModule);
    }

    public void removeModule(DankModule dankModule){
        dankModules.remove(dankModule);
    }

    public LinkedList<DankModule> getDankModules() {
        return dankModules;
    }
}