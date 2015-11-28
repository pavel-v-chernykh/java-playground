package com.example.playground.monadic.parser.combinators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.isEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static <T> Parser<T> result(T result) {
        return input -> parsedResult(result, input);
    }

    public static <T> Parser<T> error(String error) {
        return input -> parsedError(error);
    }

    @SuppressWarnings("unchecked")
    public static Parser<String> item() {
        return input -> {
            if (!input.isEmpty()) {
                return parsedResult(input.substring(0, 1), input.substring(1, input.length()));
            } else {
                return parsedError("Can not parse item");
            }
        };
    }

    public static <T1, T2> Parser<T2> bind(Parser<T1> parser, Function<T1, Parser<T2>> f) {
        return input -> parser.parse(input).flatMap(parsed -> f.apply(parsed.getItem()).parse(parsed.getRest()));
    }

    public static Parser<String> sat(Predicate<String> p) {
        return bind(Parsers.item(), i -> Optional.of(i)
                .filter(p)
                .map(Parsers::result)
                .orElseGet(() -> Parsers.error(format("Predicate is not satisfied with '%s'", i))));
    }

    public static Parser<String> exact(String item) {
        return sat(isEqual(item));
    }

    public static Parser<String> digit() {
        return sat(Predicates.isDigit());
    }

    public static Parser<String> lower() {
        return sat(Predicates.isLowerCase());
    }

    public static Parser<String> upper() {
        return sat(Predicates.isUpperCase());
    }

    public static <T> Parser<T> or(Parser<T> p1, Parser<T> p2) {
        return input -> p1.parse(input).orElseGet(() -> p2.parse(input));
    }

    public static Parser<String> letter() {
        return or(lower(), upper());
    }

    public static Parser<String> alnum() {
        return or(letter(), digit());
    }

    public static Parser<String> space() {
        return sat(Predicates.isSpace());
    }

    public static <T> Parser<List<T>> many(Parser<T> p) {
        return bind(p, i -> bind(or(many(p), result(emptyList())), o -> result(Helpers.addToList(o, i))));
    }

    public static Parser<String> spaces() {
        return bind(many(space()), spaces -> result(Helpers.concatenate(spaces)));
    }

    public static <T> Parser<List<T>> junk() {
        return bind(or(spaces(), result("")), junk -> result(emptyList()));
    }

    public static <T> Parser<T> parse(Parser<T> p) {
        return bind(junk(), junk -> p);
    }

    public static <T> Parser<T> token(Parser<T> p) {
        return bind(p, token -> bind(junk(), junk -> result(token)));
    }

    public static Parser<String> word() {
        return bind(many(letter()), letters -> result(Helpers.concatenate(letters)));
    }

    public static Parser<Long> nat() {
        return bind(many(digit()), digits -> result(Helpers.listOfStringsToLong(digits)));
    }

    public static Parser<Long> integer() {
        return or(bind(exact("-"), m -> bind(nat(), n -> result(n * -1))), nat());
    }

    public static <T> Parser<List<T>> sepby(Parser<T> p, Parser<String> sep) {
        return bind(p, i -> bind(or(many(bind(sep, s -> p)), result(emptyList())), o -> result(Helpers.addToList(o, i))));
    }

    public static <T> Parser<T> bracket(Parser<String> open, Parser<T> p, Parser<String> close) {
        return bind(open, b1 -> bind(p, i -> bind(close, b2 -> result(i))));
    }

    public static Parser<List<Long>> integers() {
        return bracket(exact("["), sepby(integer(), exact(",")), exact("]"));
    }

    private static class Predicates {
        private static Predicate<String> isDigit() {
            return s -> s.codePoints().allMatch(Character::isDigit);
        }

        private static Predicate<String> isLowerCase() {
            return s -> s.codePoints().allMatch(Character::isLowerCase);
        }

        private static Predicate<String> isUpperCase() {
            return s -> s.codePoints().allMatch(Character::isUpperCase);
        }

        private static Predicate<String> isSpace() {
            return s -> s.codePoints().allMatch(Character::isWhitespace);
        }
    }

    private static class Helpers {
        private static <T> List<T> addToList(List<T> list, T item) {
            final ArrayList<T> newList = new ArrayList<>(list);
            newList.add(0, item);
            return newList;
        }

        private static Long listOfStringsToLong(List<String> list) {
            return list.stream().mapToLong(Long::valueOf).reduce(0, (left, right) -> left * 10 + right);
        }

        public static String concatenate(List<String> letters) {
            return letters.stream().collect(Collectors.joining());
        }
    }
}
