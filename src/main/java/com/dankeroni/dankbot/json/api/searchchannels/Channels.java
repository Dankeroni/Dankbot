package com.dankeroni.dankbot.json.api.searchchannels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Channels {

    @SerializedName("_total")
    @Expose
    public int Total;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.searchchannels.Links Links;
    @SerializedName("channels")
    @Expose
    public ArrayList<Channel> channels = new ArrayList<>();
}
