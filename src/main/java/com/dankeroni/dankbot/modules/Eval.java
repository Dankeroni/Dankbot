package com.dankeroni.dankbot.modules;

import bsh.EvalError;
import bsh.Interpreter;
import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.io.BufferedReader;
import java.io.IOException;
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                channelBot.log("Failed to evaluate expression");
            }
        }
    };

    public Eval(ChannelBot channelBot) {
        super(channelBot);

        try {
            interpreter.set("bot", channelBot);
        } catch (EvalError evalError) {
        }

        commandLine = new Thread(commandLineInput);
        commandLine.setDaemon(true);
        commandLine.start();
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return this.makeExpression(message, sender);
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return this.makeExpression(message, sender);
    }

    public boolean makeExpression(String message, String sender) {
        if (sender.equalsIgnoreCase(channelBot.getAdmin()) && Utils.detectCommand(message, "!eval")) {
            try {
                this.eval(message.split(" ", 2)[1]);
            } catch (Exception e) {
                channelBot.channelMessage("Failed to evaluate expression");
            }

            return true;
        } else return false;
    }

    public void eval(String expression) {
        try {
            interpreter.eval(expression);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
    }
}
