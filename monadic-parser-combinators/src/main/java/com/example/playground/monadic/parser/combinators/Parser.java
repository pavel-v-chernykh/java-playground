package com.example.playground.monadic.parser.combinators;

import java.util.List;

@FunctionalInterface
public interface Parser<T> {
    List<Parsed<T>> parse(String input);
}
