package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.models.Module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database extends Module {

    public Connection connection;

    public Database(Bot bot) {
        super(bot);

        String url = bot.getConfig().getString("db.url"),
            username = bot.getConfig().getString("db.username"),
            password = bot.getConfig().getString("db.password");

        try {
            bot.log("Connecting to database", LogLevel.INFO);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            bot.log("Error connecting to database", LogLevel.ERROR);
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
