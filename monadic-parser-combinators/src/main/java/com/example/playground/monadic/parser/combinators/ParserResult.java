package com.example.playground.monadic.parser.combinators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Collections.singletonList;

@Getter
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
}
