package com.example.playground.monadic.parser.combinators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.BaseStream;

@Getter
@RequiredArgsConstructor
class ParserResult<T, I, S extends BaseStream<I, S>> {
    private final T result;
    private final S stream;

    public static <T, I, S extends BaseStream<I, S>> ParserResult<T, I, S> parserResult(T result, S stream) {
        return new ParserResult<>(result, stream);
    }
}
