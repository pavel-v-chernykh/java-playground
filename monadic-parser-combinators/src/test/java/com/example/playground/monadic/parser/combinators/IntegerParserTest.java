package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntegerParserTest {
    @Test
    public void shouldParseIntegerNumbers() {
        Parser parser = Parsers.integer();

        assertThat(parser.parse("0"), is(equalTo(parsedList(0L, ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList(1L, ""))));
        assertThat(parser.parse("0123456789"), is(equalTo(parsedList(123456789L, ""))));
        assertThat(parser.parse("-1"), is(equalTo(parsedList(-1L, ""))));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
