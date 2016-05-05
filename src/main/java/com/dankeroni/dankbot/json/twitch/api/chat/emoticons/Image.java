package com.dankeroni.dankbot.json.twitch.api.chat.emoticons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Image {

    @SerializedName("width")
    @Expose
    public int width;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("emoticon_set")
    @Expose
    public int emoticonSet;
}
