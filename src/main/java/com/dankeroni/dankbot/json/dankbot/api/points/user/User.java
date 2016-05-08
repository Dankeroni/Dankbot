package com.dankeroni.dankbot.json.dankbot.api.points.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("status")
    @Expose
    public int status = 200;

    @SerializedName("name")
    @Expose
    public String name;

    /*
    @SerializedName("displayName")
    @Expose
    public String displayName;
    */

    @SerializedName("points")
    @Expose
    public int points;

    public User(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
