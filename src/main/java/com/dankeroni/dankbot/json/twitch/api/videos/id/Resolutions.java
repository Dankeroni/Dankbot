package com.dankeroni.dankbot.json.twitch.api.videos.id;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Resolutions {

    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("high")
    @Expose
    public String high;
    @SerializedName("low")
    @Expose
    public String low;
    @SerializedName("chunked")
    @Expose
    public String chunked;
}
