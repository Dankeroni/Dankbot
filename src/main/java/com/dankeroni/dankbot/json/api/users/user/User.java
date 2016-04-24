package com.dankeroni.dankbot.json.api.users.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("_id")
    @Expose
    public int Id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("staff")
    @Expose
    public boolean staff;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.users.user.Links Links;
}
