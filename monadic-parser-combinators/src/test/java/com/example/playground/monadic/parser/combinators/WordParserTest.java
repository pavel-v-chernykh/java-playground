package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WordParserTest {
    @Test
    public void shouldParseManyLetters() {
        Parser parser = Parsers.word();

        assertThat(parser.parse("world"), is(equalTo(parsedResult("world", ""))));
        assertThat(parser.parse("World"), is(equalTo(parsedResult("World", ""))));
        assertThat(parser.parse("1world"), is(equalTo(parsedError("Predicate is not satisfied with '1'"))));
    }
}
