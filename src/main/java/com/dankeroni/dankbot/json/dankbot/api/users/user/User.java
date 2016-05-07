package com.dankeroni.dankbot.json.dankbot.api.users.user;

import com.dankeroni.dankbot.AccessLevel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("status")
    @Expose
    public int status = 200;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("displayName")
    @Expose
    public String displayName;

    @SerializedName("points")
    @Expose
    public int points;

    @SerializedName("timeoffline")
    @Expose
    public long timeoffline;

    @SerializedName("timeonline")
    @Expose
    public long timeonline;

    @SerializedName("accessLevel")
    @Expose
    public AccessLevel accessLevel;

    public User(String name, String displayName, int points, long timeoffline, long timeonline, AccessLevel accessLevel) {
        this.name = name;
        this.displayName = displayName;
        this.points = points;
        this.timeoffline = timeoffline;
        this.timeonline = timeonline;
        this.accessLevel = accessLevel;
    }
}
