package com.dankeroni.dankbot;

import java.util.HashMap;
import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(String message, String sender, HashMap<String, String> tags);

    default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);

        return (a, b, c) -> {
            accept(a, b, c);
            after.accept(a, b, c);
        };
    }
}
