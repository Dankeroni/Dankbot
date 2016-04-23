package com.dankeroni.dankbot.json.tmi.group.user.user.chatters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Chatters {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.tmi.group.user.user.chatters.Links Links;
    @SerializedName("chatter_count")
    @Expose
    public int chatterCount;
    @SerializedName("chatters")
    @Expose
    public Chatters_ chatters;
}
