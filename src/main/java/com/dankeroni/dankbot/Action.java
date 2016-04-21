package com.dankeroni.dankbot;

import java.util.HashMap;
import java.util.Objects;

@FunctionalInterface
public interface Action {
    void accept(String message, String sender, HashMap<String, String> tags);

    default Action andThen
            (Action after) {
        Objects.requireNonNull(after);

        return (a, b, c) -> {
            accept(a, b, c);
            after.accept(a, b, c);
        };
    }
}
