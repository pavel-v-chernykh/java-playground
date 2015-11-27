package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JunkParserTest {
    @Test
    public void shouldDiscardSpaces() {
        Parser parser = Parsers.junk();

        assertThat(parser.parse(" \t\n\r"), is(equalTo(parsedList(emptyList(), ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList(emptyList(), "1"))));
        assertThat(parser.parse(""), is(equalTo(parsedList(emptyList(), ""))));
    }
}
