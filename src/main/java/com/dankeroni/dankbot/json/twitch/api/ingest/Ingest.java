package com.dankeroni.dankbot.json.twitch.api.ingest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Ingest {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.twitch.api.ingest.Links Links;
    @SerializedName("ingests")
    @Expose
    public ArrayList<Ingest_> ingests = new ArrayList<>();
}
