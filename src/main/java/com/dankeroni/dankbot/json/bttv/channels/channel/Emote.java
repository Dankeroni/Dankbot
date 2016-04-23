package com.dankeroni.dankbot.json.bttv.channels.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Emote {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("channel")
    @Expose
    public String channel;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("imageType")
    @Expose
    public String imageType;
}
