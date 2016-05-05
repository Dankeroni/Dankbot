package com.dankeroni.dankbot.json.twitch.api.ingest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Ingest_ {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("availability")
    @Expose
    public int availability;
    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("default")
    @Expose
    public boolean _default;
    @SerializedName("url_template")
    @Expose
    public String urlTemplate;
}
