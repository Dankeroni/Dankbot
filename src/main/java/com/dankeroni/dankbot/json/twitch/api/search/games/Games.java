package com.dankeroni.dankbot.json.twitch.api.search.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Games {

    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.twitch.api.search.games.Links Links;
    @SerializedName("games")
    @Expose
    public ArrayList<Game> games = new ArrayList<>();
}
