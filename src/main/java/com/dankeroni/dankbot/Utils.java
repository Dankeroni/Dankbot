package com.dankeroni.dankbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static int clamp(int var, int min, int max) {
        if(var >= max)
            return max;
        else if(var <= min)
            return min;
        else
            return var;
    }

    public static String readUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        }
    }

    public static boolean detectCommand(String message, String command) {
        return message.equalsIgnoreCase(command) || message.toLowerCase().startsWith(command + " ");
    }
}
