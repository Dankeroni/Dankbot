package com.dankeroni.dankbot.json.twitch.api.streams.featured;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Featured {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.twitch.api.streams.featured.Links Links;
    @SerializedName("featured")
    @Expose
    public ArrayList<Featured_> featured = new ArrayList<>();
}
