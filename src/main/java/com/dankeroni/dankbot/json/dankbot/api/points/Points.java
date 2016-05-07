package com.dankeroni.dankbot.json.dankbot.api.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Points {

    @SerializedName("status")
    @Expose
    public int status = 200;

    @SerializedName("users")
    @Expose
    public ArrayList<User> users = new ArrayList<>();
}
