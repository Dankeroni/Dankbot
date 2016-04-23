package com.dankeroni.dankbot.json.api.videostop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Top {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.videostop.Links Links;
    @SerializedName("videos")
    @Expose
    public ArrayList<Video> videos = new ArrayList<>();
}
