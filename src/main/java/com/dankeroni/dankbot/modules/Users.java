package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.User;

import java.util.HashMap;

public class Users extends Module {

    public HashMap<String, User> users = new HashMap<>();

    public Users(ChannelBot channelBot) throws NullPointerException {
        super(channelBot);
    }

    public void init() {
    }

    public void loadUsers() {
        channelBot.log("Loading users", LogLevel.DEBUG);
        String admin = channelBot.getAdmin();
        Points points = channelBot.getPoints();
        this.addUser(new User(admin.toLowerCase(), admin, points.getPoints(admin), 0, 0, AccessLevel.ADMIN));
        String[] superModerators = channelBot.getConfig().getStringArray("trustedUsers");
        for (String trustedUser : superModerators)
            this.addUser(new User(trustedUser.toLowerCase(), trustedUser, points.getPoints(trustedUser), 0, 0, AccessLevel.SUPERMOD));
    }

    public void setAccessLevel(String user, AccessLevel accessLevel) {
        this.setAccessLevel(getUser(user), accessLevel);
    }

    public void setAccessLevel(User user, AccessLevel accessLevel) {
        if (users.containsKey(user.name))
            users.get(user.name).accessLevel = accessLevel;
    }

    public boolean checkAccessLevel(String user, AccessLevel accessLevel) {
        return this.checkAccessLevel(getUser(user), accessLevel);
    }

    public boolean checkAccessLevel(User user, AccessLevel accessLevel) {
        return user.accessLevel.compareTo(accessLevel) <= 0;
    }

    public void addUser(User user) {
        String name = user.name.toLowerCase();
        if (!users.containsKey(name)) {
            users.put(name, user);
        }
    }

    public User getUser(String user) {
        return users.getOrDefault(user.toLowerCase(), null);
    }

    public void removeUser(String user) {
        this.removeUser(getUser(user.toLowerCase()));
    }

    public void removeUser(User user) {
        String name = user.name.toLowerCase();
        if (users.containsKey(name))
            users.remove(name);
    }

    public void setCommands(Commands commands) {
        this.commands = commands;
    }
}
