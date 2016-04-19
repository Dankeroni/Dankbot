package com.dankeroni.dankbot.modules;

import bsh.EvalError;
import bsh.Interpreter;
import com.dankeroni.dankbot.*;

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

        commands.addCommand(new Command("!eval", this::evalFromMessage, null, AccessLevel.ADMIN, 0, 0), false);
    }

    public void evalFromMessage(String message, String sender, HashMap<String, String> tags) {
        if (sender.equalsIgnoreCase(channelBot.getAdmin())) {
            String[] messageParts = message.split(" ", 2);
            if (messageParts.length > 1 && !messageParts[1].isEmpty()) {
                try {
                    this.eval(messageParts[1]);
                } catch (EvalError e) {
                    channelBot.channelMessage("Failed to evaluate expression");
                    e.printStackTrace();
                }
            }
        }
    }

    public void eval(String expression) throws EvalError {
        interpreter.eval(expression);
    }
}
