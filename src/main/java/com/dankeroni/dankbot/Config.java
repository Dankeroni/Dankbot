package com.dankeroni.dankbot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public Properties properties;
    public String file;
    public String[] requiredOptions;
    public ChannelBot channelBot;

    public Config(ChannelBot channelBot, String file) {
        this.channelBot = channelBot;
        this.file = file;
    }

    public void loadConfig() {
        try (FileReader reader = new FileReader(file)) {
            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            channelBot.log("config.properties not found!", LogLevel.ERROR);
        }
    }

    public boolean containsRequiredOptions() {
        return containsRequiredOptions(requiredOptions);
    }

    public boolean containsRequiredOptions(String[] requiredOptions) {
        for (String requiredOption : requiredOptions) {
            if (!properties.containsKey(requiredOption)) {
                return false;
            } else if (properties.getProperty(requiredOption).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public String[] getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(String[] requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public String getString(String option) {
        return getString(option, "");
    }

    public String getString(String option, String defaultVal) {
        if (properties.containsKey(option))
            return properties.getProperty(option);
        return defaultVal;
    }

    public boolean getBoolean(String option) {
        return getBoolean(option, false);
    }

    public boolean getBoolean(String option, boolean defaultVal) {
        if (properties.containsKey(option))
            return Boolean.parseBoolean(properties.getProperty(option));
        return defaultVal;
    }

    public int getInt(String option) {
        return getInt(option, 0);
    }

    public int getInt(String option, int defaultVal) {
        if (properties.containsKey(option))
            return Integer.parseInt(properties.getProperty(option));
        return defaultVal;
    }

    public double getDouble(String option) {
        return getDouble(option, 0);
    }

    public double getDouble(String option, double defaultVal) {
        if (properties.containsKey(option))
            return Double.parseDouble(properties.getProperty(option));
        return defaultVal;
    }

    public float getFloat(String option) {
        return getFloat(option, 0);
    }

    public float getFloat(String option, float defaultVal) {
        if (properties.containsKey(option))
            return Float.parseFloat(properties.getProperty(option));
        return defaultVal;
    }

    public long getLong(String option) {
        return getLong(option, 0);
    }

    public long getLong(String option, long defaultVal) {
        if (properties.containsKey(option))
            return Long.parseLong(properties.getProperty(option));
        return defaultVal;
    }

    public String[] getStringArray(String option) {
        return getStringArray(option, new String[]{});
    }

    public String[] getStringArray(String option, String[] defaultStringArray) {
        if (properties.containsKey(option))
            return properties.getProperty(option).split(" ");
        return defaultStringArray;
    }
}
