package com.dankeroni.dankbot.json.twitch.api.streams.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("stream")
    @Expose
    public Stream stream;
    @SerializedName("_links")
    @Expose
    public Links__ Links;
}
