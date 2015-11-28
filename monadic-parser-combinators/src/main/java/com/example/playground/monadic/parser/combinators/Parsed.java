package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class Parsed<T> {
    private final T item;
    private final String rest;

    public static <T> Result<Parsed<T>, String> parsedResult(T result, String stream) {
        return Result.result(new Parsed<>(result, stream));
    }

    public static <T> Result<Parsed<T>, String> parsedError(String error) {
        return Result.error(error);
    }
}
