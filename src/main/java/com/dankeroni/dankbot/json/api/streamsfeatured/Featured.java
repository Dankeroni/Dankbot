package com.dankeroni.dankbot.json.api.streamsfeatured;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Featured {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.streamsfeatured.Links Links;
    @SerializedName("featured")
    @Expose
    public ArrayList<Featured_> featured = new ArrayList<>();
}
