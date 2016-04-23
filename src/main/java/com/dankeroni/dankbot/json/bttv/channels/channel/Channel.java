package com.dankeroni.dankbot.json.bttv.channels.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("urlTemplate")
    @Expose
    public String urlTemplate;
    @SerializedName("bots")
    @Expose
    public ArrayList<String> bots = new ArrayList<>();
    @SerializedName("emotes")
    @Expose
    public ArrayList<Emote> emotes = new ArrayList<>();
}
