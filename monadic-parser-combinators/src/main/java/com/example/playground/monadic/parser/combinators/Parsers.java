package com.example.playground.monadic.parser.combinators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.example.playground.monadic.parser.combinators.Parsed.combine;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static java.util.function.Predicate.isEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static Parser result(String result) {
        return input -> parsedList(result, input);
    }

    public static Parser zero() {
        return input -> parsedList();
    }

    @SuppressWarnings("unchecked")
    public static Parser item() {
        return input ->
                Optional.of(input)
                        .filter(Predicates.nonEmptyString())
                        .map(i -> parsedList(i.substring(0, 1), i.substring(1, i.length())))
                        .orElse(parsedList());
    }

    public static Parser bind(Parser parser, Function<String, Parser> f) {
        return input ->
                Optional.of(parser.parse(input))
                        .filter(Predicates.nonEmptyList())
                        .map(results -> f.apply(results.get(0).getItem()).parse(results.get(0).getRest()))
                        .orElse(parsedList());
    }

    public static Parser sat(Predicate<String> p) {
        return bind(Parsers.item(), t ->
                Optional.of(t)
                        .filter(p)
                        .map(Parsers::result)
                        .orElseGet(Parsers::zero));
    }

    public static Parser exact(String item) {
        return sat(isEqual(item));
    }

    public static Parser digit() {
        return sat(Predicates.isDigit());
    }

    public static Parser lower() {
        return sat(Predicates.isLowerCase());
    }

    public static Parser upper() {
        return sat(Predicates.isUpperCase());
    }

    public static Parser plus(Parser p1, Parser p2) {
        return input -> combine(p1.parse(input), p2.parse(input));
    }

    public static Parser letter() {
        return plus(lower(), upper());
    }

    public static Parser alnum() {
        return plus(letter(), digit());
    }

    public static Parser many(Parser p) {
        return bind(p, i -> bind(plus(many(p), result("")), o -> result(i + o)));
    }

    public static Parser word() {
        return many(letter());
    }

    public static Parser nat() {
        return many(digit());
    }

    public static Parser integer() {
        return plus(bind(exact("-"), m -> bind(nat(), n -> result(m + n))), nat());
    }

    private static class Predicates {
        private static <T> Predicate<List<T>> nonEmptyList() {
            return l -> !l.isEmpty();
        }

        private static Predicate<String> nonEmptyString() {
            return s -> !s.isEmpty();
        }

        private static Predicate<String> isDigit() {
            return s -> s.codePoints().allMatch(Character::isDigit);
        }

        private static Predicate<String> isLowerCase() {
            return s -> s.codePoints().allMatch(Character::isLowerCase);
        }

        private static Predicate<String> isUpperCase() {
            return s -> s.codePoints().allMatch(Character::isUpperCase);
        }
    }
}
