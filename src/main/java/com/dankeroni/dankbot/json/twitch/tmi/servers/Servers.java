package com.dankeroni.dankbot.json.twitch.tmi.servers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Servers {

    @SerializedName("cluster")
    @Expose
    public String cluster;
    @SerializedName("servers")
    @Expose
    public ArrayList<String> servers = new ArrayList<>();
    @SerializedName("websockets_servers")
    @Expose
    public ArrayList<String> websocketsServers = new ArrayList<>();
}
