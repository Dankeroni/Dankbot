package com.dankeroni.dankbot;

public class User {

    public String name, capitalizedName;
    public int points;
    public long timeonline, timeoffline;
    public AccessLevel accessLevel;

    public User(String name, String capitalizedName, int points, long timeonline, long timeoffline, AccessLevel accessLevel) {
        this.name = name;
        this.capitalizedName = capitalizedName;
        this.points = points;
        this.timeonline = timeonline;
        this.timeoffline = timeoffline;
        this.accessLevel = accessLevel;
    }
}
