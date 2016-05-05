package com.dankeroni.dankbot.json.twitch.api.users.user.follows.channels;

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
    public com.dankeroni.dankbot.json.twitch.api.users.user.follows.channels.Links Links;
    @SerializedName("notifications")
    @Expose
    public boolean notifications;
    @SerializedName("channel")
    @Expose
    public Channel channel;
}
