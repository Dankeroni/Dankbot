package com.dankeroni.dankbot.json.dankbot.api.users;

import com.dankeroni.dankbot.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Users {

    @SerializedName("status")
    @Expose
    public int status = 200;

    @SerializedName("users")
    @Expose
    public ArrayList<User> users = new ArrayList<>();
}
