package com.dankeroni.dankbot.json.api.searchgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Game {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("popularity")
    @Expose
    public int popularity;
    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("giantbomb_id")
    @Expose
    public int giantbombId;
    @SerializedName("box")
    @Expose
    public Box box;
    @SerializedName("logo")
    @Expose
    public Logo logo;
    @SerializedName("_links")
    @Expose
    public Links_ Links;
}
