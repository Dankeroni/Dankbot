package com.dankeroni.dankbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

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

    public static String[] makeArgs(String message) {
        String[] messageWords = message.split(" ");
        return Arrays.copyOfRange(messageWords, 1, messageWords.length);
    }

    public static String format(String message, Object... inputArgs) {
        if(message.contains("{") && message.contains("}")) {
            String formatedString = message;
            String[] messageArgs = makeArgs((String) inputArgs[0]);

            HashMap<String, String> args = new HashMap<>();

            for(int i = 0; i < messageArgs.length; i++)
                args.put("{arg" + String.valueOf(i) + "}", messageArgs[i]);

            HashMap<String, String> tags = (HashMap<String, String>) inputArgs[1];
            args.put("{sender}", tags.get("display-name"));

            for(HashMap.Entry<String, String> arg: args.entrySet())
                formatedString = formatedString.replace(arg.getKey(), arg.getValue());

            return formatedString;
        } else {
            return message;
        }
    }
}
