package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResult;
import static java.util.function.Predicate.isEqual;
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

    public static <T1, T2, E, I, S extends BaseStream<I, S>> Parser<T2, E, I, S> bind(Parser<T1, E, I, S> p1,
                                                                                      Function<T1, Parser<T2, E, I, S>> f) {
        return input -> p1.parse(input).flatMap(pr -> f.apply(pr.getResult()).parse(pr.getStream()));
    }

    public static <T, E, S extends BaseStream<T, S>> Parser<T, E, T, S> sat(Predicate<T> p, E error) {
        return Parsers.<T, T, E, T, S>bind(Parsers.item(error), t1 ->
                Optional.of(t1).filter(p).map(Parsers::<T, E, T, S>result).orElse(Parsers.error(error)));
    }

    public static <T1, T2, E, I, S extends BaseStream<I, S>> Parser<Pair<T1, T2>, E, I, S> seq(Parser<T1, E, I, S> p1,
                                                                                               Parser<T2, E, I, S> p2) {
        return Parsers.bind(p1, t1 -> Parsers.bind(p2, t2 -> Parsers.result(Pair.pair(t1, t2))));
    }

    public static <T, E, S extends BaseStream<T, S>> Parser<T, E, T, S> exact(T item, E error) {
        return Parsers.sat(isEqual(item), error);
    }
}
