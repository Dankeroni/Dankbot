package com.dankeroni.dankbot.json.bttv.emotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Restrictions {

    @SerializedName("channels")
    @Expose
    public ArrayList<Object> channels = new ArrayList<>();
    @SerializedName("games")
    @Expose
    public ArrayList<Object> games = new ArrayList<>();
}
