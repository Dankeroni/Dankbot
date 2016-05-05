package com.dankeroni.dankbot.models;

import java.util.HashMap;

@FunctionalInterface
public interface Variable {

    String get(String message, String sender, HashMap<String, String> tags);
}
