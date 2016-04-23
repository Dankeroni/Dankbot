package com.dankeroni.dankbot.json.api.channels.channel.follows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("_links")
    @Expose
    public Links_ Links;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("type")
    @Expose
    public String type;
}
