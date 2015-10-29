package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.BaseStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResult;
import static java.util.stream.Stream.generate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static <T, E, I, S extends BaseStream<I, S>> Parser<T, E, I, S> result(T result) {
        return input -> Result.result(parserResult(result, input));
    }

    public static <T, E, I, S extends BaseStream<I, S>> Parser<T, E, I, S> error(E error) {
        return input -> Result.error(error);
    }

    public static <T, E, S extends BaseStream<T, S>> Parser<T, E, T, S> item(E error) {
        return input -> Optional.of(input.iterator())
                .filter(Iterator::hasNext)
                .map(i -> {
                    @SuppressWarnings("unchecked") final S stream = (S) generate(i::next);
                    return Result.<ParserResult<T, T, S>, E>result(parserResult(i.next(), stream));
                })
                .orElse(Result.error(error));
    }
}
