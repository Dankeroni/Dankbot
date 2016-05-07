package com.dankeroni.dankbot.json.dankbot.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotFoundError {
    @SerializedName("status")
    @Expose
    public int status = 404;
    @SerializedName("error")
    @Expose
    public String error = "Not Found";
    @SerializedName("message")
    @Expose
    public String message;
}
