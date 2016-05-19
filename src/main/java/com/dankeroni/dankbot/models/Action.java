package com.dankeroni.dankbot.models;

@FunctionalInterface
public interface Action {

    void accept(String message, String sender, TwitchTags tags);
}
