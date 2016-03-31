package com.dankeroni.dankbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static ChannelBot channelBot;

    public static void setChannelBot(ChannelBot channelBot) {
        Utils.channelBot = channelBot;
    }

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

    public static String format(String message, String senderMessage, HashMap<String, String> tags) {
        if(message.contains("{") && message.contains("}")) {
            String formatedString = message;

            HashMap<String, String> args = new HashMap<>();

            for(int i2 = 0; i2 < 10; i2++)
                args.put("{arg" + String.valueOf(i2) + "}", null);

            String[] messageArgs = makeArgs(senderMessage);
            for(int i = 0; i < messageArgs.length; i++)
                args.put("{arg" + String.valueOf(i) + "}", messageArgs[i]);

            args.put("{sender}", tags.get("display-name"));

            args.put("{time}", time());
            args.put("{botuptime}", botUpTime());

            for(HashMap.Entry<String, String> arg: args.entrySet()) {
                String key = arg.getKey(), value = arg.getValue();

                if(message.contains(key))
                    if(value != null && !value.trim().isEmpty()) {
                        formatedString = formatedString.replace(key, value);
                    } else {
                        return tags.get("display-name") + ", invalid arguments";
                    }
            }

            return formatedString;
        } else {
            return message;
        }
    }

    public static String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    public static String botUpTime() {
        long millis = System.currentTimeMillis() - channelBot.getTimeStarted();
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        if (days > 0) {
            return String.format("%s Days %s Hours %s Minutes %s Seconds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%s Hours %s Minutes %s Seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%s Minutes %s Seconds", minutes, seconds);
        } else {
            return String.format("%s Seconds", seconds);
        }
    }
}
