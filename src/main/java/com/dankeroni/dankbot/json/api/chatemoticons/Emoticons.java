package com.dankeroni.dankbot.json.api.chatemoticons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Emoticons {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.chatemoticons.Links Links;
    @SerializedName("emoticons")
    @Expose
    public ArrayList<Emoticon> emoticons = new ArrayList<>();

}
