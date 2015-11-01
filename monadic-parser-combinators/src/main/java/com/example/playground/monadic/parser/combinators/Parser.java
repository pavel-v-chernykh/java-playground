package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;

import java.util.stream.BaseStream;

@FunctionalInterface
public interface Parser<T, I, S extends BaseStream<I, S>> {
    Result<ParserResult<T, I, S>, ParserError> parse(S input);
}
