package com.example.playground.monadic.parser.combinators;

import lombok.Value;

@Value
public class Parsed<T> {
    private final T item;
    private final String rest;

    public static <T> Parsed<T> of(T item, String rest) {
        return new Parsed<>(item, rest);
    }
}
