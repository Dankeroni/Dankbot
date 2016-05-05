package com.dankeroni.dankbot.json.twitch.api.videos.top;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("display_name")
    @Expose
    public String displayName;
}
