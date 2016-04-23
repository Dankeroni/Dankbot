package com.dankeroni.dankbot.json.api.streamssummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Summary {

    @SerializedName("channels")
    @Expose
    public int channels;
    @SerializedName("viewers")
    @Expose
    public int viewers;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.streamssummary.Links Links;
}
