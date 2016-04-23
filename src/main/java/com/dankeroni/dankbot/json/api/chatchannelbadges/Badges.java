package com.dankeroni.dankbot.json.api.chatchannelbadges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Badges {

    @SerializedName("global_mod")
    @Expose
    public GlobalMod globalMod;
    @SerializedName("admin")
    @Expose
    public Admin admin;
    @SerializedName("broadcaster")
    @Expose
    public Broadcaster broadcaster;
    @SerializedName("mod")
    @Expose
    public Mod mod;
    @SerializedName("staff")
    @Expose
    public Staff staff;
    @SerializedName("turbo")
    @Expose
    public Turbo turbo;
    @SerializedName("subscriber")
    @Expose
    public Subscriber subscriber;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.chatchannelbadges.Links Links;
}
