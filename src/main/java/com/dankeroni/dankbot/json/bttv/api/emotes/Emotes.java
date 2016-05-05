package com.dankeroni.dankbot.json.bttv.api.emotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Emotes {

    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("urlTemplate")
    @Expose
    public String urlTemplate;
    @SerializedName("emotes")
    @Expose
    public ArrayList<Emote> emotes = new ArrayList<>();
}
