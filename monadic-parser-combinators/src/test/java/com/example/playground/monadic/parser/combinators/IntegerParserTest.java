package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntegerParserTest {
    @Test
    public void shouldParseIntegerNumbers() {
        Parser parser = Parsers.integer();

        assertThat(parser.parse("0"), is(equalTo(Parser.result(0L, ""))));
        assertThat(parser.parse("1"), is(equalTo(Parser.result(1L, ""))));
        assertThat(parser.parse("0123456789"), is(equalTo(Parser.result(123456789L, ""))));
        assertThat(parser.parse("-1"), is(equalTo(Parser.result(-1L, ""))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
