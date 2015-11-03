package com.example.playground.monadic.parser.combinators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.BaseStream;

@Getter
@RequiredArgsConstructor
class ParserResult<T, I, S extends BaseStream<I, S>> {
    private final T result;
    private final S stream;

    public static <T, I, S extends BaseStream<I, S>> ParserResult<T, I, S> parserResult(T result, S stream) {
        return new ParserResult<>(result, stream);
    }

    public static <T, I, S extends BaseStream<I, S>> List<ParserResult<T, I, S>> parserResultList(T result, S stream) {
        final List<ParserResult<T, I, S>> parserResultList = new ArrayList<>();
        parserResultList.add(parserResult(result, stream));
        return parserResultList;
    }
}
