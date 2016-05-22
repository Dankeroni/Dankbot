package com.dankeroni.dankbot.models;

import spark.Request;
import spark.Response;
import spark.Route;

public interface JsonRoute extends Route {

    @Override
    default Object handle(Request request, Response response) throws Exception {
        response.type("application/json");
        return handle1(request, response);
    }

    Object handle1(Request request, Response response) throws Exception;
}
