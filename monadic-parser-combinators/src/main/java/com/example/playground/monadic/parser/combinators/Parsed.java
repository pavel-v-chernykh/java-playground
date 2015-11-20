package com.example.playground.monadic.parser.combinators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class Parsed {
    private final String item;
    private final String rest;

    public static Parsed parsed(String result, String stream) {
        return new Parsed(result, stream);
    }

    public static List<Parsed> parsedList(String result, String stream) {
        return singletonList(parsed(result, stream));
    }

    public static List<Parsed> parsedList() {
        return emptyList();
    }

    public static List<Parsed> combine(List<Parsed> pr1, List<Parsed> pr2) {
        List<Parsed> results = new ArrayList<>();
        results.addAll(pr1);
        results.addAll(pr2);
        return results;
    }
}
