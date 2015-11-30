package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DigitParserTest {
    @Test
    public void shouldParseOnlyOneDigit() {
        final Parser parser = Parsers.digit();

        assertThat(parser.parse("1world"), is(equalTo(Parser.result("1", "world"))));
        assertThat(parser.parse("world"), is(equalTo(Parser.error("Predicate is not satisfied with 'w'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
