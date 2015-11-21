package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntegersParserTest {
    @Test
    public void shouldParseIntegers() {
        Parser parser = Parsers.integers();

        assertThat(parser.parse("[]"), is(equalTo(parsedList())));
        assertThat(parser.parse("[1]"), is(equalTo(parsedList("1", ""))));
        assertThat(parser.parse("[0,1,2,3]"), is(equalTo(parsedList("0123", ""))));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
