package com.dankeroni.dankbot.json.api.streamschannel;

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
