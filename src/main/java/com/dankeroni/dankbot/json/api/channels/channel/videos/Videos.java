package com.dankeroni.dankbot.json.api.channels.channel.videos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Videos {

    @SerializedName("_total")
    @Expose
    public int Total;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.channels.channel.videos.Links Links;
    @SerializedName("videos")
    @Expose
    public ArrayList<Video> videos = new ArrayList<>();
}
