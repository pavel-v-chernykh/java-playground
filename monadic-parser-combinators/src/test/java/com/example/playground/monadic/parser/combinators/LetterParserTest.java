package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LetterParserTest {
    @Test
    public void shouldParseLowerCaseOrUpperCaseItem() {
        Parser parser = Parsers.letter();

        assertThat(parser.parse("world"), is(equalTo(parsedResult("w", "orld"))));
        assertThat(parser.parse("World"), is(equalTo(parsedResult("W", "orld"))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Can not parse item"))));
    }
}
