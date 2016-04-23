package com.dankeroni.dankbot.json.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Root {

    @SerializedName("token")
    @Expose
    public Token token;
    @SerializedName("_links")
    @Expose
    public com.dankeroni.dankbot.json.api.Links Links;
}
