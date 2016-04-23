package com.dankeroni.dankbot.json.api.searchstreams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("mature")
    @Expose
    public boolean mature;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("broadcaster_language")
    @Expose
    public String broadcasterLanguage;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("game")
    @Expose
    public String game;
    @SerializedName("language")
    @Expose
    public String language;
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
    @SerializedName("delay")
    @Expose
    public Object delay;
    @SerializedName("logo")
    @Expose
    public Object logo;
    @SerializedName("banner")
    @Expose
    public Object banner;
    @SerializedName("video_banner")
    @Expose
    public Object videoBanner;
    @SerializedName("background")
    @Expose
    public Object background;
    @SerializedName("profile_banner")
    @Expose
    public Object profileBanner;
    @SerializedName("profile_banner_background_color")
    @Expose
    public Object profileBannerBackgroundColor;
    @SerializedName("partner")
    @Expose
    public boolean partner;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("followers")
    @Expose
    public int followers;
    @SerializedName("_links")
    @Expose
    public Links__ Links;
}
