package com.dankeroni.dankbot.json.twitch.api.streams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Stream {

    @SerializedName("game")
    @Expose
    public String game;
    @SerializedName("viewers")
    @Expose
    public int viewers;
    @SerializedName("average_fps")
    @Expose
    public double averageFps;
    @SerializedName("delay")
    @Expose
    public int delay;
    @SerializedName("video_height")
    @Expose
    public int videoHeight;
    @SerializedName("is_playlist")
    @Expose
    public boolean isPlaylist;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("channel")
    @Expose
    public Channel channel;
    @SerializedName("preview")
    @Expose
    public Preview preview;
    @SerializedName("_links")
    @Expose
    public Links_ Links;
}
