package com.dankeroni.dankbot;

import java.util.LinkedList;

public class DankHandler {

    public LinkedList<DankModule> dankModules = new LinkedList<>();

    public void checkChannelMessage(String message, String sender){
        checkChannelMessage:{
            for(DankModule dankModule : dankModules){
                if(dankModule.checkChannelMessage(message, sender))break;
            }
        }
    }

    public void checkWhisperMessage(String message, String sender){
        for(DankModule dankModule : dankModules){
            if(dankModule.checkWhisperMessage(message, sender))break;
        }
    }

    public void addModule(DankModule dankModule){
        dankModules.add(dankModule);
    }

    public void removeModule(DankModule dankModule){
        dankModules.remove(dankModule);
    }
}