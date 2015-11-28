package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;

@FunctionalInterface
public interface Parser<T> {
    Result<Parsed<T>, String> parse(String input);
}
