package com.dankeroni.dankbot.json.twitch.api.users.user.follows.channels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("_links")
    @Expose
    public Links_ Links;
    @SerializedName("background")
    @Expose
    public Object background;
    @SerializedName("banner")
    @Expose
    public Object banner;
    @SerializedName("broadcaster_language")
    @Expose
    public String broadcasterLanguage;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("game")
    @Expose
    public String game;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("mature")
    @Expose
    public boolean mature;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("partner")
    @Expose
    public boolean partner;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("video_banner")
    @Expose
    public String videoBanner;
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
    @SerializedName("followers")
    @Expose
    public int followers;
    @SerializedName("profile_banner")
    @Expose
    public String profileBanner;
    @SerializedName("profile_banner_background_color")
    @Expose
    public String profileBannerBackgroundColor;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("language")
    @Expose
    public String language;
}
