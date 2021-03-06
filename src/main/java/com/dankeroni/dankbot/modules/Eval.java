package com.dankeroni.dankbot.modules;

import bsh.EvalError;
import bsh.Interpreter;
import com.dankeroni.dankbot.AccessLevel;
import com.dankeroni.dankbot.Bot;
import com.dankeroni.dankbot.LogLevel;
import com.dankeroni.dankbot.Utils;
import com.dankeroni.dankbot.models.ActionCommand;
import com.dankeroni.dankbot.models.Module;
import com.dankeroni.dankbot.models.TwitchTags;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Eval extends Module {

    public Interpreter interpreter = new Interpreter();

    public Runnable commandLineInput = () -> {
        BufferedReader commandLineReader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (bot.isRunning()) {
            try {
                if ((line = commandLineReader.readLine()) != null)
                    this.eval(line);
            } catch (Exception e) {
                bot.log("Failed to evaluate expression", LogLevel.WARN);
                e.printStackTrace();
            }
        }
    };

    public Eval(Bot bot) {
        super(bot);

        try {
            interpreter.set("bot", bot);
        } catch (EvalError evalError) {
            bot.log("Error initializing Eval module", LogLevel.WARN);
            return;
        }
        Utils.runThreaded(commandLineInput);

        commands.addActionCommand(new ActionCommand("!eval", this::evalFromMessage, AccessLevel.ADMIN, 0, 0));
    }

    public void evalFromMessage(String message, String sender, TwitchTags tags) {
        String[] messageParts = message.split(" ", 2);
        if (messageParts.length > 1 && !messageParts[1].isEmpty()) {
            try {
                this.eval(messageParts[1]);
            } catch (Exception e) {
                bot.channelMessage("Failed to evaluate expression");
                e.printStackTrace();
            }
        }
    }

    public void eval(String expression) throws EvalError {
        interpreter.eval(expression);
    }
}
