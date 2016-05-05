package com.dankeroni.dankbot.json.twitch.api.chat.emoticons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Emoticon {

    @SerializedName("regex")
    @Expose
    public String regex;
    @SerializedName("images")
    @Expose
    public ArrayList<Image> images = new ArrayList<>();
}
