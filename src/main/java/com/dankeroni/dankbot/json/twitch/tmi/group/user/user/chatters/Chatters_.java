package com.dankeroni.dankbot.json.twitch.tmi.group.user.user.chatters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Chatters_ {

    @SerializedName("moderators")
    @Expose
    public ArrayList<String> moderators = new ArrayList<>();
    @SerializedName("staff")
    @Expose
    public ArrayList<String> staff = new ArrayList<>();
    @SerializedName("admins")
    @Expose
    public ArrayList<String> admins = new ArrayList<>();
    @SerializedName("global_mods")
    @Expose
    public ArrayList<String> globalMods = new ArrayList<>();
    @SerializedName("viewers")
    @Expose
    public ArrayList<String> viewers = new ArrayList<>();
}
