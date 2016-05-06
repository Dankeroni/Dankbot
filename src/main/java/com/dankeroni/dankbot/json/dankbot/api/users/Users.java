package com.dankeroni.dankbot.json.dankbot.api.users;

import com.dankeroni.dankbot.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class Users {

    @SerializedName("users")
    @Expose
    public ArrayList<User> users = new ArrayList<>();
}
