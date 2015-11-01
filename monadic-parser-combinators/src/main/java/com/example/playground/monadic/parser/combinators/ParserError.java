package com.example.playground.monadic.parser.combinators;

import lombok.Value;

@Value
public class ParserError {
    private String message;

    public static ParserError parserError(String message) {
        return new ParserError(message);
    }
}
