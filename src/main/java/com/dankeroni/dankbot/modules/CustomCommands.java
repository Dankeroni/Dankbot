package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.stream.Stream;

public class CustomCommands extends Module {

    public CommandHandler commandHandler = new CommandHandler(channelBot);
    public Path commandFile = Paths.get(channelBot.getPath() + "config.commands");
    public File commandFile2 = new File(channelBot.getPath() + "config.commands");
    public boolean commandFileExists;

    public CustomCommands(ChannelBot channelBot) {
        super(channelBot);

        if (commandFile2.exists()) {
            commandFileExists = true;
            try (Stream<String> lines = Files.lines(commandFile)) {
                lines.forEachOrdered(line -> this.addCustomCommand("!addcom " + line));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                commandFileExists = commandFile2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                commandFileExists = false;
            }
        }
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return commandHandler.checkChannelMessage(message, sender, tags) || check(message, sender);
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return commandHandler.checkWhisperMessage(message, sender, tags) || check(message, sender);
    }

    public boolean check(String message, String sender) {
        if (Utils.detectCommand(message, "!rawcom")) {
            try {
                channelBot.channelMessage(commandHandler.getCommands().get(Utils.makeArgs(message)[0]).getResponse());
                return true;
            } catch (NullPointerException e) {
                channelBot.channelMessage("This command doesn't exist!");
            }
        }

        if (!sender.equalsIgnoreCase(channelBot.getAdmin())) return false;

        if (Utils.detectCommand(message, "!addcom")) {
            this.addCustomCommandToConfig(message);
            return true;

        } else if (Utils.detectCommand(message, "!removecom")) {
            this.removeCustomCommand(message);
            return true;

        } else {
            return false;
        }
    }

    public boolean addCustomCommand(String message) {
        String[] words = message.split(" ", 3);
        String commandName = words[1];
        String response = words[2];
        return commandName.trim().isEmpty() || response.trim().isEmpty() || commandHandler.addCommand(commandName, new Command(response));
    }

    public void addCustomCommandToConfig(String message) {
        if (commandFileExists && addCustomCommand(message)) {
            try {
                Files.write(commandFile, (String.join(" ", (CharSequence[]) Utils.makeArgs(message)) + "\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCustomCommand(String message) {
        String commandName = message.split(" ")[1];
        if (!commandFileExists || !commandHandler.removeCommand(commandName)) return;

        File tempFile = new File(channelBot.getPath() + "config_temp.commands");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(commandFile2));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(commandName + " ")) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            Files.deleteIfExists(commandFile);
            if (!tempFile.renameTo(commandFile2))
                channelBot.log("ERROR: Removing custom command from datafile failed, try cleaning it up manually");
        } catch (IOException e) {
            e.printStackTrace();
            channelBot.log("ERROR: Removing custom command from datafile failed, try cleaning it up manually");
        }
    }
}
