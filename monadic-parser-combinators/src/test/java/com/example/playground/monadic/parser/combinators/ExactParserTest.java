package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExactParserTest {
    @Test
    public void shouldParseOnlyExactChar() {
        final Parser parser = Parsers.exact("w");

        assertThat(parser.parse("world"), is(equalTo(parsedList("w", "orld"))));
        assertThat(parser.parse("World"), is(equalTo(parsedList())));
        assertThat(parser.parse("1World"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
