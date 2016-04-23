package com.dankeroni.dankbot.json.api.teams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Teams {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.teams.Links Links;
    @SerializedName("teams")
    @Expose
    public ArrayList<Team> teams = new ArrayList<>();
}
