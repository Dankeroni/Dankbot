package com.dankeroni.dankbot.json.api.channels.channel.teams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Team {

    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("info")
    @Expose
    public String info;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("banner")
    @Expose
    public Object banner;
    @SerializedName("background")
    @Expose
    public String background;
    @SerializedName("_links")
    @Expose
    public Links_ Links;
}
