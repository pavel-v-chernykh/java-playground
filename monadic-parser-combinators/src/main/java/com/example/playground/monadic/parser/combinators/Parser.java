package com.example.playground.monadic.parser.combinators;

import java.util.List;
import java.util.stream.BaseStream;

@FunctionalInterface
public interface Parser<T, I, S extends BaseStream<I, S>> {
    List<ParserResult<T, I, S>> parse(S input);
}
