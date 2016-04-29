package com.dankeroni.dankbot;

import org.fusesource.jansi.Ansi;

public enum LogLevel {

    ERROR(Ansi.ansi().fg(Ansi.Color.RED).a("[ERROR]").reset().toString()),
    WARN(Ansi.ansi().fg(Ansi.Color.YELLOW).a("[WARN]").reset().toString()),
    INFO(Ansi.ansi().fg(Ansi.Color.GREEN).a("[INFO]").reset().toString()),
    DEBUG(Ansi.ansi().fg(Ansi.Color.CYAN).a("[DEBUG]").reset().toString()),
    TRACE(Ansi.ansi().fg(Ansi.Color.WHITE).a("[TRACE]").reset().toString());

    public String colouredMessage;

    LogLevel(String colouredMessage) {
        this.colouredMessage = colouredMessage;
    }

    public String toString() {
        return colouredMessage;
    }
}
