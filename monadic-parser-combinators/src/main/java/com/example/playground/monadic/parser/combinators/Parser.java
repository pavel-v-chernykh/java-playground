package com.example.playground.monadic.parser.combinators;

import java.util.List;

@FunctionalInterface
public interface Parser {
    List<ParserResult> parse(String input);
}
