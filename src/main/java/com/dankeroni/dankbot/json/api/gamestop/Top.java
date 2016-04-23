package com.dankeroni.dankbot.json.api.gamestop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Top {

    @SerializedName("_total")
    @Expose
    public int Total;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.gamestop.Links Links;
    @SerializedName("top")
    @Expose
    public ArrayList<Top_> top = new ArrayList<>();
}
