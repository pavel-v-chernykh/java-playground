package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UpperParserTest {
    @Test
    public void shouldParseOnlyUpperCaseItem() {
        final Parser parser = Parsers.upper();

        assertThat(parser.parse("World"), is(equalTo(parsedList("W", "orld"))));
        assertThat(parser.parse("world"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
