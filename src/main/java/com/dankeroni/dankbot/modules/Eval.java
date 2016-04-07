package com.dankeroni.dankbot.modules;

import bsh.EvalError;
import bsh.Interpreter;
import com.dankeroni.dankbot.ChannelBot;
import com.dankeroni.dankbot.Module;
import com.dankeroni.dankbot.Utils;

import java.util.HashMap;

public class Eval extends Module {

    public Interpreter interpreter = new Interpreter();

    public Eval(ChannelBot channelBot) {
        super(channelBot);

        try {
            interpreter.set("bot", channelBot);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
    }

    public boolean checkChannelMessage(String message, String sender, HashMap<String, String> tags) {
        return this.eval(message, sender);
    }

    public boolean checkWhisperMessage(String message, String sender, HashMap<String, String> tags) {
        return this.eval(message, sender);
    }

    public boolean eval(String message, String sender) {
        if (sender.equalsIgnoreCase(channelBot.getAdmin()) && Utils.detectCommand(message, "!eval")) {
            String expression = message.split(" ", 2)[1];

            try {
                interpreter.eval(expression);
            } catch (EvalError evalError) {
                evalError.printStackTrace();
                channelBot.channelMessage("Failed to evaluate expression");
            }

            return true;
        } else return false;
    }
}
