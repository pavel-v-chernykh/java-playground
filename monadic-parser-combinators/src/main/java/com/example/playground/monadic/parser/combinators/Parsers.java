package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.example.playground.monadic.parser.combinators.Parsers.Helpers.*;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.joining;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parsers {
    public static <T> Parser<T> result(T result) {
        return input -> Parser.result(result, input);
    }

    public static <T> Parser<T> error(String error) {
        return input -> Parser.error(error);
    }

    @SuppressWarnings("unchecked")
    public static Parser<String> item() {
        return input -> {
            if (!input.isEmpty()) {
                return Parser.result(input.substring(0, 1), input.substring(1, input.length()));
            } else {
                return Parser.error("Can not parse item");
            }
        };
    }

    public static <T1, T2> Parser<T2> bind(Parser<T1> parser, Function<T1, Parser<T2>> f) {
        return input -> parser.parse(input).flatMap(parsed -> f.apply(parsed.getItem()).parse(parsed.getRest()));
    }

    public static Parser<String> sat(Predicate<String> p) {
        return bind(Parsers.item(), i -> {
            if (p.test(i)) {
                return result(i);
            } else {
                return error(format("Predicate is not satisfied with '%s'", i));
            }
        });
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

    @SafeVarargs
    public static <T> Parser<T> or(Parser<T> p1, Parser<T>... others) {
        return input -> {
            Result<Parsed<T>, String> result = p1.parse(input);
            if (result.isError()) {
                for (Parser<T> parser : others) {
                    result = parser.parse(input);
                    if (result.isResult()) {
                        break;
                    }
                }
            }
            return result;
        };
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
        return bind(p, i -> bind(or(many(p), result(emptyList())), o -> result(prependList(o, i))));
    }

    public static Parser<String> spaces() {
        return bind(many(space()), spaces -> result(concatenate(spaces)));
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

    public static Parser<String> seq(Parser<String> p) {
        return bind(many(p), item -> result(concatenate(item)));
    }

    public static Parser<String> word() {
        return seq(letter());
    }

    public static Parser<String> string(String string) {
        return input -> {
            if (input.length() < string.length()) {
                return Parser.error(format("Can not parse string '%s'", string));
            }
            if (!input.substring(0, string.length()).equals(string)) {
                return Parser.error(format("Can not parse string '%s'", string));
            }
            return Parser.result(string, input.substring(string.length(), input.length()));
        };
    }

    public static Parser<Long> nat() {
        return bind(many(digit()), digits -> result(listOfStringsToLong(digits)));
    }

    public static Parser<Long> integer() {
        return or(bind(exact("-"), m -> bind(nat(), n -> result(n * -1))), nat());
    }

    public static <T> Parser<List<T>> sepby(Parser<T> p, Parser<String> sep) {
        return bind(p, i -> bind(or(many(bind(sep, s -> p)), result(emptyList())), o -> result(prependList(o, i))));
    }

    public static <T> Parser<T> bracket(Parser<String> open, Parser<T> p, Parser<String> close) {
        return bind(open, b1 -> bind(p, i -> bind(close, b2 -> result(i))));
    }

    public static <T1, T2, T3, T4> Parser<T4> ternary(Parser<T1> p1, Parser<T2> p2, Parser<T3> p3, TriFunction<T1, T2, T3, Parser<T4>> f) {
        return bind(p1, t1 -> bind(p2, t2 -> bind(p3, t3 -> f.apply(t1, t2, t3))));
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

    static class Helpers {
        static <T> List<T> prependList(List<T> list, T item) {
            final ArrayList<T> newList = new ArrayList<>(list);
            newList.add(0, item);
            return newList;
        }

        static Long listOfStringsToLong(List<String> list) {
            return list.stream().mapToLong(Long::valueOf).reduce(0, (left, right) -> left * 10 + right);
        }

        public static String concatenate(List<String> letters) {
            return letters.stream().collect(joining());
        }
    }
}
