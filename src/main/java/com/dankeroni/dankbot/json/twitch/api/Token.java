package com.dankeroni.dankbot.json.twitch.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Token {

    @SerializedName("authorization")
    @Expose
    public Authorization authorization;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("valid")
    @Expose
    public boolean valid;
}
