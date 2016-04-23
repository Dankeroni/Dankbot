package com.dankeroni.dankbot.json.api.users.user.follows.channels.target;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Target {

    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.users.user.follows.channels.target.Links Links;
    @SerializedName("notifications")
    @Expose
    public boolean notifications;
    @SerializedName("channel")
    @Expose
    public Channel channel;
}
