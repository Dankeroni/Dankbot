package com.dankeroni.dankbot.json.api.channelschannelfollows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Follow {

    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.channelschannelfollows.Links Links;
    @SerializedName("notifications")
    @Expose
    public boolean notifications;
    @SerializedName("user")
    @Expose
    public User user;
}
