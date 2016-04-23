
package com.dankeroni.dankbot.json.api.videosis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Id {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("broadcast_id")
    @Expose
    public int broadcastId;
    @SerializedName("broadcast_type")
    @Expose
    public String broadcastType;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tag_list")
    @Expose
    public String tagList;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_id")
    @Expose
    public String Id;
    @SerializedName("recorded_at")
    @Expose
    public String recordedAt;
    @SerializedName("game")
    @Expose
    public String game;
    @SerializedName("length")
    @Expose
    public double length;
    @SerializedName("preview")
    @Expose
    public String preview;
    @SerializedName("thumbnails")
    @Expose
    public ArrayList<Thumbnail> thumbnails = new ArrayList<>();
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("fps")
    @Expose
    public Fps fps;
    @SerializedName("resolutions")
    @Expose
    public Resolutions resolutions;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.videosis.Links Links;
    @SerializedName("channel")
    @Expose
    public Channel channel;
}
