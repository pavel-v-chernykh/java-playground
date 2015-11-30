package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExactParserTest {
    @Test
    public void shouldParseOnlyExactChar() {
        final Parser parser = Parsers.exact("w");

        assertThat(parser.parse("world"), is(equalTo(Parser.result("w", "orld"))));
        assertThat(parser.parse("World"), is(equalTo(Parser.error("Predicate is not satisfied with 'W'"))));
        assertThat(parser.parse("1World"), is(equalTo(Parser.error("Predicate is not satisfied with '1'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
