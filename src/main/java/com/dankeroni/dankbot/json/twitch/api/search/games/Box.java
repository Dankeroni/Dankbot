package com.dankeroni.dankbot.json.twitch.api.search.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Box {

    @SerializedName("large")
    @Expose
    public String large;
    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("small")
    @Expose
    public String small;
    @SerializedName("template")
    @Expose
    public String template;
}
