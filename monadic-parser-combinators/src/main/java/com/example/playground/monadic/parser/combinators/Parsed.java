package com.example.playground.monadic.parser.combinators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class Parsed<T> {
    private final T item;
    private final String rest;

    public static <T> Parsed<T> parsed(T result, String stream) {
        return new Parsed<>(result, stream);
    }

    public static <T> List<Parsed<T>> parsedList(T result, String stream) {
        return singletonList(parsed(result, stream));
    }

    public static <T> List<Parsed<T>> parsedList() {
        return emptyList();
    }

    public static <T> List<Parsed<T>> or(List<Parsed<T>> pr1, List<Parsed<T>> pr2) {
        if (pr1.isEmpty()) {
            return pr2;
        } else {
            return pr1;
        }
    }
}
