package com.dankeroni.dankbot;

import com.dankeroni.dankbot.json.twitch.tmi.group.user.user.chatters.Chatters;
import com.dankeroni.dankbot.json.twitch.tmi.group.user.user.chatters.Chatters_;
import com.dankeroni.dankbot.models.TwitchTags;
import com.dankeroni.dankbot.models.Variable;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static boolean argsConfigured = false;
    public static Bot bot;
    public static Config config;
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat detailedTime = new SimpleDateFormat("HH:mm:ss,SSS");
    public static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
    public static SimpleDateFormat logDate = new SimpleDateFormat("yy-MM-dd");
    public static HashMap<String, Variable> args = new HashMap<>();

    static {
        time.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static void setBot(Bot bot) {
        Utils.bot = bot;
        Utils.config = bot.getConfig();
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

    public static void runThreaded(Runnable runnable) {
        runThreaded(runnable, true);
    }

    public static void runThreaded(Runnable runnable, boolean daemon) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(daemon);
        thread.start();
    }

    public static void runDelayed(Runnable runnable, long millis) {
        runDelayed(runnable, millis, true);
    }

    public static void runDelayed(Runnable runnable, long millis, boolean daemon) {
        runThreaded(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            }
            runnable.run();
        }, daemon);
    }

    public static String[] makeArgs(String message) {
        String[] messageWords = message.split(" ");
        return Arrays.copyOfRange(messageWords, 1, messageWords.length);
    }

    public static String format(String message, String sender, String senderMessage, TwitchTags tags) {
        return format(message, sender, senderMessage, tags, true);
    }

    public static String format(String message, String sender, String senderMessage, TwitchTags tags, boolean considerConfig) {
        if (!message.contains("{") || !message.contains("}")) return message;

        String[] messageArgs = makeArgs(senderMessage);
        for (int i2 = 0; i2 < 10; i2++)
            args.put("{arg" + String.valueOf(i2) + "}", null);

        for (int i = 0; i < messageArgs.length; i++) {
            int finalI = i;
            args.put("{arg" + String.valueOf(i) + "}", (a, b, c) -> messageArgs[finalI]);
        }

        if (!argsConfigured) {
            args.put("{sender}", (a, b, c) -> c.displayName);
            args.put("{realsender}", (a, b, c) -> b);
            args.put("{time}", Utils::time);
            args.put("{detailedtime}", Utils::detailedTime);
            args.put("{date}", Utils::date);
            args.put("{botuptime}", Utils::botUpTime);
            args.put("{botname}", (a, b, c) -> bot.getName());
            args.put("{commitHash}", (a, b, c) -> bot.getCommitHash());
            args.put("{commitNumber}", (a, b, c) -> String.valueOf(bot.getCommitNumber()));
            args.put("{branch}", (a, b, c) -> bot.getBranch());
            args.put("{admin}", (a, b, c) -> bot.getAdmin());
            args.put("{channel}", (a, b, c) -> bot.getChannel());
            args.put("{chattersCount}", (a, b, c) -> String.valueOf(chattersCount()));

            argsConfigured = true;
        }

        for (HashMap.Entry<String, Variable> arg : args.entrySet()) {
            String key = arg.getKey();

            if (message.contains(key) && (!considerConfig || config.getBoolean(key, true))) {
                String value;
                try {
                    value = arg.getValue().get(message, sender, tags);
                } catch (NullPointerException e) {
                    value = null;
                }
                if (value != null && !value.trim().isEmpty()) message = message.replace(key, value);
                else return tags.displayName + ", invalid arguments";
            }
        }
        return message;
    }

    public static String time(String message, String sender, TwitchTags tags) {
        return time.format(new Date());
    }

    public static String detailedTime() {
        return detailedTime(null, null, null);
    }

    public static String detailedTime(String message, String sender, TwitchTags tags) {
        return detailedTime.format(new Date());
    }

    public static String logDate() {
        return logDate.format(new Date());
    }

    public static String date() {
        return date(null, null, null);
    }

    public static String date(String message, String sender, TwitchTags tags) {
        return date.format(new Date());
    }

    public static String botUpTime(String message, String sender, TwitchTags tags) {
        long millis = System.currentTimeMillis() - bot.getTimeStarted();
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 1) stringBuilder.append(days).append(" days and ");
        else if (days == 1) stringBuilder.append(days).append(" day and ");

        if (hours > 1) stringBuilder.append(hours).append(" hours and ");
        else if (hours == 1) stringBuilder.append(hours).append(" hour and ");

        if (minutes > 1) stringBuilder.append(minutes).append(" minutes and ");
        else if (minutes == 1) stringBuilder.append(minutes).append(" minute and ");

        if (seconds > 1) stringBuilder.append(seconds).append(" seconds");
        else if (seconds == 1) stringBuilder.append(seconds).append(" second");

        String time = stringBuilder.toString().trim();
        return time.endsWith("and") ? time.substring(0, time.length() - 4) : time;
    }

    public static int chattersCount() {
        int count = 0;
        for (ArrayList<String> chattersList : getAllChattersLists())
            count += chattersList.size();
        return count;
    }

    public static ArrayList<ArrayList<String>> getAllChattersLists() {
        Chatters chatters = null;
        try {
            chatters = new Gson().fromJson(Utils.readUrl("https://tmi.twitch.tv/group/user/" + bot.getChannel().substring(1) + "/chatters"), Chatters.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Chatters_ chatters_ = chatters.chatters;
        ArrayList<ArrayList<String>> allChattersLists = new ArrayList<>();
        allChattersLists.add(chatters_.moderators);
        allChattersLists.add(chatters_.staff);
        allChattersLists.add(chatters_.admins);
        allChattersLists.add(chatters_.globalMods);
        allChattersLists.add(chatters_.viewers);
        return allChattersLists;
    }
}
