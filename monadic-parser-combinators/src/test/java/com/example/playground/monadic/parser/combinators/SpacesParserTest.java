package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpacesParserTest {
    @Test
    public void shouldParseSpaces() {
        Parser parser = Parsers.spaces();

        assertThat(parser.parse(" \t\n\r"), is(equalTo(parsedList(" \t\n\r", ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
