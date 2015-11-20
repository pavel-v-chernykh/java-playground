package com.example.playground.monadic.parser.combinators;

import java.util.List;

@FunctionalInterface
public interface Parser {
    List<Parsed> parse(String input);
}
