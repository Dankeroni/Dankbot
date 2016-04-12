package com.dankeroni.dankbot.modules;

import bsh.EvalError;
import bsh.Interpreter;
import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Eval extends Module {

    public Interpreter interpreter = new Interpreter();
    public Thread commandLine;

    public Runnable commandLineInput = () -> {
        BufferedReader commandLineReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (true) {
            try {
                if ((line = commandLineReader.readLine()) != null)
                    this.eval(line);
            } catch (Exception e) {
                channelBot.log("Failed to evaluate expression", LogLevel.WARN);
                e.printStackTrace();
            }
        }
    };

    public Eval(ChannelBot channelBot) {
        super(channelBot);

        try {
            interpreter.set("bot", channelBot);
        } catch (EvalError evalError) {
            channelBot.log("Error initializing Eval module", LogLevel.WARN);
            return;
        }

        commandLine = new Thread(commandLineInput);
        commandLine.setDaemon(true);
        commandLine.start();
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return this.evalFromMessage(message, sender);
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return this.evalFromMessage(message, sender);
    }

    public boolean evalFromMessage(String message, String sender) {
        if (!Utils.detectCommand(message, "!eval")) return false;

        if (sender.equalsIgnoreCase(channelBot.getAdmin())) {
            String[] messageParts = message.split(" ", 2);
            if (messageParts.length > 1 && !messageParts[1].trim().isEmpty()) {
                try {
                    this.eval(messageParts[1].trim());
                } catch (Exception e) {
                    channelBot.channelMessage("Failed to evaluate expression");
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void eval(String expression) throws EvalError {
        interpreter.eval(expression);
    }
}
