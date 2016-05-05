package com.dankeroni.dankbot.json.bttv.api.emotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Emote {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("channel")
    @Expose
    public Object channel;
    @SerializedName("restrictions")
    @Expose
    public Restrictions restrictions;
    @SerializedName("imageType")
    @Expose
    public String imageType;
}
