package com.dankeroni.dankbot;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleHandler {

    public ArrayList<Module> modules = new ArrayList<>();

    public void checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        for (Module module : modules) {
            if (module.checkChannelMessage(message, sender, tags)) {
                break;
            }
        }
    }

    public void checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        for (Module module : modules) {
            if (module.checkWhisperMessage(message, sender, tags)) {
                break;
            }
        }
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
