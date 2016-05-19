package com.dankeroni.dankbot.models;

@FunctionalInterface
public interface Variable {

    String get(String message, String sender, TwitchTags tags);
}
