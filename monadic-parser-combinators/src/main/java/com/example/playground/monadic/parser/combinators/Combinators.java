package com.example.playground.monadic.parser.combinators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.stream.BaseStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Combinators {
    public static <T1, T2, E, I, S extends BaseStream<I, S>> Parser<T2, E, I, S> bind(Parser<T1, E, I, S> p1,
                                                                                      Function<T1, Parser<T2, E, I, S>> f) {
        return input -> p1.parse(input).flatMap(pr -> f.apply(pr.getResult()).parse(pr.getStream()));
    }
}
