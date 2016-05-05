package com.dankeroni.dankbot.json.twitch.api.videos.top;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Fps {

    @SerializedName("audio_only")
    @Expose
    public int audioOnly;
    @SerializedName("medium")
    @Expose
    public double medium;
    @SerializedName("mobile")
    @Expose
    public double mobile;
    @SerializedName("high")
    @Expose
    public double high;
    @SerializedName("low")
    @Expose
    public double low;
    @SerializedName("chunked")
    @Expose
    public double chunked;
}
