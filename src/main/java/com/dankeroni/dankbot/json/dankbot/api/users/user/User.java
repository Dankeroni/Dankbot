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

    @SerializedName("timeOffline")
    @Expose
    public long timeOffline;

    @SerializedName("timeOnline")
    @Expose
    public long timeOnline;

    @SerializedName("accessLevel")
    @Expose
    public AccessLevel accessLevel;

    public User(String name, String displayName, int points, long timeOffline, long timeOnline, AccessLevel accessLevel) {
        this.name = name;
        this.displayName = displayName;
        this.points = points;
        this.timeOffline = timeOffline;
        this.timeOnline = timeOnline;
        this.accessLevel = accessLevel;
    }
}
