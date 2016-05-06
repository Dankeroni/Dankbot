package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.json.dankbot.api.users.Users;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.route.RouteOverview.enableRouteOverview;

public class Api extends Module {

    public Gson gson = new Gson();

    public Api(Bot bot) {
        super(bot);

        bot.log("Starting api", LogLevel.INFO);
        enableRouteOverview();
        get("/api/users/:user", (req, res) -> gson.toJson(users.getUser(req.params(":user").toLowerCase())));
        get("/api/users", (req, res) -> {
            Users users = new Users();
            HashMap<String, User> userHashMap = this.users.getUsers();
            users.users = new ArrayList<>(userHashMap.values());
            return gson.toJson(users);
        });
        get("/api/points", (req, res) -> gson.toJson(bot.getPoints().getPointsList()));
    }
}
