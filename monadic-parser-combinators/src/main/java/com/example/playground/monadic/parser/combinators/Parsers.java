package com.example.playground.monadic.parser.combinators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Stream.generate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static <T, I, S extends BaseStream<I, S>> Parser<T, I, S> result(T result) {
        return input -> parserResultList(result, input);
    }

    public static <T, I, S extends BaseStream<I, S>> Parser<T, I, S> zero() {
        return input -> emptyList();
    }

    @SuppressWarnings("unchecked")
    public static <T, S extends BaseStream<T, S>> Parser<T, T, S> item() {
        return input -> Optional.of(input.iterator())
                .filter(Helpers.iteratorHasNext())
                .map(i -> parserResultList(i.next(), (S) generate(i::next)))
                .orElse(emptyList());
    }

    public static <T1, T2, I, S extends BaseStream<I, S>> Parser<T2, I, S> bind(Parser<T1, I, S> parser,
                                                                                Function<T1, Parser<T2, I, S>> f) {
        return input -> Optional.of(parser.parse(input))
                .filter(Helpers.nonEmptyList())
                .map(parserResults -> f.apply(parserResults.get(0).getResult()).parse(parserResults.get(0).getStream()))
                .orElse(emptyList());
    }

    public static <T, S extends BaseStream<T, S>> Parser<T, T, S> sat(Predicate<T> p) {
        return Parsers.<T, T, T, S>bind(Parsers.item(), t ->
                Optional.of(t).filter(p).map(Parsers::<T, T, S>result).orElse(Parsers.zero()));
    }

    public static <T, S extends BaseStream<T, S>> Parser<T, T, S> exact(T item) {
        return Parsers.sat(isEqual(item));
    }

    public static Parser<Integer, Integer, IntStream> digit() {
        return Parsers.sat(Character::isDigit);
    }

    public static Parser<Integer, Integer, IntStream> lower() {
        return Parsers.sat(Character::isLowerCase);
    }

    public static Parser<Integer, Integer, IntStream> upper() {
        return Parsers.sat(Character::isUpperCase);
    }

    private static class Helpers {
        private static <T> Predicate<List<T>> nonEmptyList() {
            return ts -> !ts.isEmpty();
        }

        private static Predicate<Iterator> iteratorHasNext() {
            return Iterator::hasNext;
        }
    }
}
