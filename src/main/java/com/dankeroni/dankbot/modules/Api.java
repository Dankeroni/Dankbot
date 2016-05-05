package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Module;
import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.route.RouteOverview.enableRouteOverview;

public class Api extends Module {

    public Gson gson = new Gson();

    public Api(ChannelBot channelBot) {
        super(channelBot);

        channelBot.log("Starting api", LogLevel.INFO);
        enableRouteOverview();
        get("/users/:user", (req, res) -> gson.toJson(users.getUser(req.params(":user").toLowerCase())));
        get("/points", (req, res) -> gson.toJson(channelBot.getPoints().getPointsList()));
    }

}
