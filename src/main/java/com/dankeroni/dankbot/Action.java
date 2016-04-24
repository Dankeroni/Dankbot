package com.dankeroni.dankbot;

import java.util.HashMap;

@FunctionalInterface
public interface Action {

    void accept(String message, String sender, HashMap<String, String> tags);
}
