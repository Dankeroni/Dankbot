package com.dankeroni.dankbot.modules;

import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.ActionCommand;
import com.dankeroni.dankbot.models.MessageCommand;
import com.dankeroni.dankbot.models.Module;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.stream.Stream;

public class CustomCommands extends Module {

    public Path commandFile = Paths.get(bot.getPath() + "save.commands");
    public File commandFile2 = new File(bot.getPath() + "save.commands");
    public boolean commandFileExists;

    public CustomCommands(Bot bot) {
        super(bot);

        if (commandFile2.exists()) {
            commandFileExists = true;
            bot.log("Loading custom commands", LogLevel.DEBUG);
            try (Stream<String> lines = Files.lines(commandFile)) {
                lines.forEachOrdered(line -> this.addCustomCommand("!addcom " + line, false));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bot.log("Commands file not found, generating new one", LogLevel.INFO);
                commandFileExists = commandFile2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                bot.log("Unable to generate commands file, custom commands will not work until you fix the problem and restart the bot!", LogLevel.WARN);
                commandFileExists = false;
            }
        }

        commands.addActionCommand(new ActionCommand("!rawcom", this::rawCommand, AccessLevel.USER, 2, 4));
        commands.addActionCommand(new ActionCommand("!addcom", this::addCustomCommandToConfig, AccessLevel.SUPERMOD, 1, 1));
        commands.addActionCommand(new ActionCommand("!removecom", this::removeCustomCommand, AccessLevel.SUPERMOD, 1, 1));
    }

    public void rawCommand(String message, String sender, HashMap<String, String> tags) {
        try {
            String commandName = Utils.makeArgs(message)[0];
            MessageCommand command = commands.getCustomCommands().get(commandName);
            command = commands.getMessageCommands().getOrDefault(commandName, command);
            bot.channelMessage(command.getMessage());
        } catch (NullPointerException e) {
            bot.channelMessage("This command doesn't exist!");
        }
    }

    public boolean addCustomCommand(String message) {
        return addCustomCommand(message, true);
    }

    public boolean addCustomCommand(String message, boolean log) {
        String[] words = message.split(" ", 3);
        String command = words[1];
        String response = words[2];
        return command.isEmpty() || response.isEmpty() || commands.addCustomCommand(new MessageCommand(command, response, AccessLevel.USER, 4, 10), log);
    }

    public void addCustomCommandToConfig(String message, String sender, HashMap<String, String> tags) {
        if (commandFileExists && addCustomCommand(message)) {
            try {
                Files.write(commandFile, (String.join(" ", (CharSequence[]) Utils.makeArgs(message)) + System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCustomCommand(String message, String sender, HashMap<String, String> tags) {
        String commandName = message.split(" ")[1];
        if (!commandFileExists || !commands.removeCustomCommand(commandName)) return;

        File tempFile = new File(bot.getPath() + "save_temp.commands");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(commandFile2));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (Utils.detectCommand(currentLine, commandName)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            Files.deleteIfExists(commandFile);
            if (!tempFile.renameTo(commandFile2))
                bot.log("Removing custom command from datafile failed, try cleaning it up manually", LogLevel.WARN);
        } catch (IOException e) {
            e.printStackTrace();
            bot.log("Removing custom command from datafile failed, try cleaning it up manually", LogLevel.WARN);
        }
    }
}
