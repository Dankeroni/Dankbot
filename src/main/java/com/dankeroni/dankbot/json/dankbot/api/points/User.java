package com.dankeroni.dankbot.json.dankbot.api.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

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
}
