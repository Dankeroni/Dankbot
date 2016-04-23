package com.dankeroni.dankbot.json.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Links {

    @SerializedName("channel")
    @Expose
    public String channel;
    @SerializedName("users")
    @Expose
    public String users;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("channels")
    @Expose
    public String channels;
    @SerializedName("chat")
    @Expose
    public String chat;
    @SerializedName("streams")
    @Expose
    public String streams;
    @SerializedName("ingests")
    @Expose
    public String ingests;
    @SerializedName("teams")
    @Expose
    public String teams;
    @SerializedName("search")
    @Expose
    public String search;
}
