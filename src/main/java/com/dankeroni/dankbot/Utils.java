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
import java.util.function.Supplier;

public class Utils {

    public static boolean argsConfigured = false;
    public static ChannelBot channelBot;
    public static Config config;
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat detailedTime = new SimpleDateFormat("HH:mm:ss,SSS");
    public static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
    public static HashMap<String, Supplier> args = new HashMap<>();

    static {
        time.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static void setChannelBot(ChannelBot channelBot) {
        Utils.channelBot = channelBot;
        Utils.config = channelBot.getConfig();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max)
            return max;
        else if (var <= min)
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
        message = message.toLowerCase();
        command = command.toLowerCase();
        return message.equals(command) || message.startsWith(command + " ");
    }

    public static String[] makeArgs(String message) {
        String[] messageWords = message.split(" ");
        return Arrays.copyOfRange(messageWords, 1, messageWords.length);
    }

    public static String format(String message, String sender, String senderMessage, HashMap<String, String> tags) {
        return format(message, sender, senderMessage, tags, true);
    }

    public static String format(String message, String sender, String senderMessage, HashMap<String, String> tags, boolean considerConfig) {
        if (message.contains("{") && message.contains("}")) {
            String[] messageArgs = makeArgs(senderMessage);
            for (int i2 = 0; i2 < 10; i2++)
                args.put("{arg" + String.valueOf(i2) + "}", null);

            for (int i = 0; i < messageArgs.length; i++) {
                int finalI = i;
                args.put("{arg" + String.valueOf(i) + "}", () -> messageArgs[finalI]);
            }

            if (!argsConfigured) {
                args.put("{sender}", () -> tags.get("display-name"));
                args.put("{realsender}", () -> sender);
                args.put("{time}", Utils::time);
                args.put("{detailedtime}", Utils::detailedTime);
                args.put("{date}", Utils::date);
                args.put("{botuptime}", Utils::botUpTime);
                args.put("{botname}", () -> channelBot.getName());
                args.put("{commitHash}", () -> channelBot.getCommitHash());
                args.put("{commitNumber}", () -> String.valueOf(channelBot.getCommitNumber()));
                args.put("{branch}", () -> channelBot.getBranch());
                args.put("{admin}", () -> channelBot.getAdmin());
                args.put("{channel}", () -> channelBot.getChannel());

                argsConfigured = true;
            }

            for (HashMap.Entry<String, Supplier> arg : args.entrySet()) {
                String key = arg.getKey(), value;
                try {
                    value = (String) arg.getValue().get();
                } catch (NullPointerException e) {
                    value = null;
                }

                if (message.contains(key) && (!considerConfig || config.getBoolean(key, true)))
                    if (value != null && !value.trim().isEmpty()) {
                        message = message.replace(key, value);
                    } else {
                        return tags.get("display-name") + ", invalid arguments";
                    }
            }
        }
        return message;
    }

    public static boolean checkAccessLevel(String user, AccessLevel accessLevel) {
        return channelBot.getUserAccessLevels().getOrDefault(user, AccessLevel.USER).compareTo(accessLevel) <= 0;
    }

    public static String time() {
        return time.format(new Date());
    }

    public static String detailedTime() {
        return detailedTime.format(new Date());
    }

    public static String date() {
        return date.format(new Date());
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
            return String.format("%sd %sh %sm %ss", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%sh %sm %ss", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%sm %ss", minutes, seconds);
        } else {
            return String.format("%ss", seconds);
        }
    }
}
