package com.example.playground.monadic.parser.combinators;

import lombok.Value;

@Value
public class Pair<L, R> {
    private final L left;
    private final R right;

    public static <L, R> Pair<L, R> pair(L left, R right) {
        return new Pair<>(left, right);
    }
}
