package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.BaseStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static <T, E, I, S extends BaseStream<I, S>> Parser<T, E, I, S> result(T result) {
        return input -> Result.result(parserResult(result, input));
    }

    public static <T, E, I, S extends BaseStream<I, S>> Parser<T, E, I, S> error(E error) {
        return input -> Result.error(error);
    }
}
