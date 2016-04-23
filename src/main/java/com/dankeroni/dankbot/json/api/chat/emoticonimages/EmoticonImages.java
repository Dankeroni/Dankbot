package com.dankeroni.dankbot.json.api.chat.emoticonimages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;

@Generated("org.jsonschema2pojo")
public class EmoticonImages {

    @SerializedName("emoticons")
    @Expose
    public ArrayList<Emoticon> emoticons = new ArrayList<>();
}
