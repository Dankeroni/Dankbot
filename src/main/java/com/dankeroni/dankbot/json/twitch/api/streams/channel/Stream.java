package com.dankeroni.dankbot.json.twitch.api.streams.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Stream {

    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("game")
    @Expose
    public String game;
    @SerializedName("viewers")
    @Expose
    public int viewers;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("video_height")
    @Expose
    public int videoHeight;
    @SerializedName("average_fps")
    @Expose
    public double averageFps;
    @SerializedName("delay")
    @Expose
    public int delay;
    @SerializedName("is_playlist")
    @Expose
    public boolean isPlaylist;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.twitch.api.streams.channel.Links Links;
    @SerializedName("preview")
    @Expose
    public Preview preview;
    @SerializedName("channel")
    @Expose
    public Channel_ channel;
}
