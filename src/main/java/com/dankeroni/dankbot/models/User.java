package com.dankeroni.dankbot.models;

import com.dankeroni.dankbot.AccessLevel;

public class User {

    public String name, displayName;
    public int points;
    public long timeonline, timeoffline;
    public AccessLevel accessLevel;

    public User(String name, String displayName, int points, long timeonline, long timeoffline, AccessLevel accessLevel) {
        this.name = name;
        this.displayName = displayName;
        this.points = points;
        this.timeonline = timeonline;
        this.timeoffline = timeoffline;
        this.accessLevel = accessLevel;
    }
}
