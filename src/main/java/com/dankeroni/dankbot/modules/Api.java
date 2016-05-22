package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.json.dankbot.api.NotFoundError;
import com.dankeroni.dankbot.models.JsonRoute;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.User;
import com.google.gson.Gson;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.port;

public class Api extends Module {

    public Gson gson = new Gson();

    public JsonRoute users1 = (request, response) -> {
        HashMap<String, User> userHashMap = users.getUsers();
        if (!userHashMap.isEmpty()) {
            com.dankeroni.dankbot.json.dankbot.api.users.Users users = new com.dankeroni.dankbot.json.dankbot.api.users.Users();
            users.users = new ArrayList<>(userHashMap.values());
            return users;
        } else {
            NotFoundError notFoundError = new NotFoundError();
            notFoundError.message = "No users found";
            return notFoundError;
        }
    },

    usersUser = (request, response) -> {
        String user = request.params(":user");
        if (users.userExists(user)) {
            User user1 = users.getUser(user);
            return new com.dankeroni.dankbot.json.dankbot.api.users.user.User(
                user1.name, user1.displayName, user1.points, user1.timeOffline, user1.timeOnline, user1.accessLevel);
        } else {
            NotFoundError notFoundError = new NotFoundError();
            notFoundError.message = "User not found";
            return notFoundError;
        }
    },

    points = (request, response) -> {
        HashMap<String, Integer> pointsList = bot.points.getPointsList();
        if (!pointsList.isEmpty()) {
            com.dankeroni.dankbot.json.dankbot.api.points.Points points1 = new com.dankeroni.dankbot.json.dankbot.api.points.Points();
            for (HashMap.Entry<String, Integer> entry : pointsList.entrySet()) {
                com.dankeroni.dankbot.json.dankbot.api.points.User user = new com.dankeroni.dankbot.json.dankbot.api.points.User();
                user.name = entry.getKey();
                user.points = entry.getValue();
                points1.users.add(user);
            }
            return points1;
        } else {
            NotFoundError notFoundError = new NotFoundError();
            notFoundError.message = "No users found";
            return notFoundError;
        }
    },

    pointsUser = (request, response) -> {
        HashMap<String, Integer> pointsList = bot.points.getPointsList();
        String user = request.params(":user").toLowerCase();
        if (pointsList.containsKey(user)) {
            return new com.dankeroni.dankbot.json.dankbot.api.points.user.User(user, pointsList.get(user));
        } else {
            NotFoundError notFoundError = new NotFoundError();
            notFoundError.message = "User not found";
            return notFoundError;
        }
    };

    public Api(Bot bot) {
        super(bot);

        bot.log("Starting api", LogLevel.INFO);
        port(80);
        getJson("/api/users/:user", usersUser);
        getJson("/api/users", users1);
        getJson("/api/points", points);
        getJson("/api/points/:user", pointsUser);
    }

    public void getJson(String path, Route route) {
        get(path, route, gson::toJson);
    }
}
