package com.dankeroni.dankbot.json.api.streamsfeatured;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Featured_ {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("sponsored")
    @Expose
    public boolean sponsored;
    @SerializedName("priority")
    @Expose
    public int priority;
    @SerializedName("scheduled")
    @Expose
    public boolean scheduled;
    @SerializedName("stream")
    @Expose
    public Stream stream;
}
