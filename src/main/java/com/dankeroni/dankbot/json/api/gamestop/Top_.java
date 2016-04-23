package com.dankeroni.dankbot.json.api.gamestop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Top_ {

    @SerializedName("viewers")
    @Expose
    public int viewers;
    @SerializedName("channels")
    @Expose
    public int channels;
    @SerializedName("game")
    @Expose
    public Game game;
}
