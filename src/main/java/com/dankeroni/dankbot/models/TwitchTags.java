package com.dankeroni.dankbot.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TwitchTags {

    public HashMap<String, String> tags = new HashMap<>();
    public ArrayList<String> badges = new ArrayList<>(), emoteSets = new ArrayList<>();
    public String color, displayName, userType;
    public boolean mod, subscriber, turbo;
    public int roomId, userId;

    public TwitchTags(HashMap<String, String> tags) {
        this.tags = tags;
        String badgesString = tags.get("badges");
        if (badgesString != null) Collections.addAll(this.badges, badgesString.split(","));
        this.color = tags.get("color");
        this.displayName = tags.get("display-name");
        String emoteSetsString = tags.get("emote-sets");
        if (emoteSetsString != null) Collections.addAll(this.emoteSets, emoteSetsString.split("/"));
        this.mod = tags.get("mod").equals("1");
        this.roomId = Integer.parseInt(tags.get("room-id"));
        this.subscriber = tags.get("subscriber").equals("1");
        this.turbo = tags.get("turbo").equals("1");
        this.userId = Integer.parseInt(tags.get("user-id"));
        this.userType = tags.get("user-type");
    }
}
