package com.dankeroni.dankbot;

import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.TwitchTags;

import java.util.ArrayList;

public class Modules {

    public ArrayList<Module> modules = new ArrayList<>();

    public void onChannelMessage(String message, String sender, TwitchTags tags) {
        for (Module module : modules)
            module.onChannelMessage(message, sender, tags);
    }

    public void onWhisperMessage(String message, String sender, TwitchTags tags) {
        for (Module module : modules)
            module.onWhisperMessage(message, sender, tags);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}
