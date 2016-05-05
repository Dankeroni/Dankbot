package com.dankeroni.dankbot.json.twitch.api.channels.channel.teams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Teams {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.twitch.api.channels.channel.teams.Links Links;
    @SerializedName("teams")
    @Expose
    public ArrayList<Team> teams = new ArrayList<>();
}
