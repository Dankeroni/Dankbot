package com.dankeroni.dankbot;

import java.util.ArrayList;
import java.util.HashMap;

public class Modules {

    public ArrayList<Module> modules = new ArrayList<>();

    public void onChannelMessage(String message, String sender, HashMap<String, String> tags) {
        for (Module module : modules)
            module.onChannelMessage(message, sender, tags);
    }

    public void onWhisperMessage(String message, String sender, HashMap<String, String> tags) {
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
