package com.example.playground.monadic.parser.combinators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Combinators {
    public static <T1, T2, E, I, S extends BaseStream<I, S>> Parser<T2, E, I, S> bind(Parser<T1, E, I, S> p1,
                                                                                      Function<T1, Parser<T2, E, I, S>> f) {
        return input -> p1.parse(input).flatMap(pr -> f.apply(pr.getResult()).parse(pr.getStream()));
    }

    public static <T, E, S extends BaseStream<T, S>> Parser<T, E, T, S> sat(Predicate<T> p, E error) {
        return Combinators.<T, T, E, T, S>bind(Parsers.item(error), t1 ->
                Optional.of(t1).filter(p).map(Parsers::<T, E, T, S>result).orElse(Parsers.error(error)));
    }
}
