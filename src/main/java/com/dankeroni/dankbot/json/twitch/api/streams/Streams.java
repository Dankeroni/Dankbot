package com.dankeroni.dankbot.json.twitch.api.streams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Streams {

    @SerializedName("_total")
    @Expose
    public int Total;
    @SerializedName("streams")
    @Expose
    public ArrayList<Stream> streams = new ArrayList<>();
    @SerializedName("_links")
    @Expose
    public Links__ Links;
}
