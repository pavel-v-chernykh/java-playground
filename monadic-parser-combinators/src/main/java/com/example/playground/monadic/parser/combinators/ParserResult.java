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
class ParserResult {
    private final String result;
    private final String stream;

    public static ParserResult parserResult(String result, String stream) {
        return new ParserResult(result, stream);
    }

    public static List<ParserResult> parserResultList(String result, String stream) {
        return singletonList(parserResult(result, stream));
    }

    public static List<ParserResult> parserResultList() {
        return emptyList();
    }
}
