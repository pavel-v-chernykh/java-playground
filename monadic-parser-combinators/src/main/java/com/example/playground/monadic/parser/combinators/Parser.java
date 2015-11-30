package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;

@FunctionalInterface
public interface Parser<T> {
    static <T> Result<Parsed<T>, String> result(T result, String stream) {
        return Result.result(Parsed.of(result, stream));
    }

    static <T> Result<Parsed<T>, String> error(String error) {
        return Result.error(error);
    }

    Result<Parsed<T>, String> parse(String input);
}
