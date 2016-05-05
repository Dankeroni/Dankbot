package com.dankeroni.dankbot.json.twitch.api.users.user.follows.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Channels {

    @SerializedName("follows")
    @Expose
    public ArrayList<Follow> follows = new ArrayList<>();
    @SerializedName("_total")
    @Expose
    public int Total;
    @SerializedName("_links")
    @Expose
    public Links__ Links;
}
