package com.dankeroni.dankbot.models;

import com.dankeroni.dankbot.AccessLevel;

public class User {

    public String name, displayName;
    public int points;
    public long timeOnline, timeOffline;
    public AccessLevel accessLevel;

    public User(String name, String displayName, int points, long timeOnline, long timeOffline, AccessLevel accessLevel) {
        this.name = name;
        this.displayName = displayName;
        this.points = points;
        this.timeOnline = timeOnline;
        this.timeOffline = timeOffline;
        this.accessLevel = accessLevel;
    }
}
