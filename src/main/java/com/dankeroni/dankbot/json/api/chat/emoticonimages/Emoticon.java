package com.dankeroni.dankbot.json.api.chat.emoticonimages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Emoticon {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("emoticon_set")
    @Expose
    public int emoticonSet;
}
