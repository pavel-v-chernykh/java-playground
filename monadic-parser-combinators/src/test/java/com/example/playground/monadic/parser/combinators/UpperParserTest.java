package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UpperParserTest {
    @Test
    public void shouldParseOnlyUpperCaseItem() {
        final Parser parser = Parsers.upper();

        assertThat(parser.parse("World"), is(equalTo(Parser.result("W", "orld"))));
        assertThat(parser.parse("world"), is(equalTo(Parser.error("Predicate is not satisfied with 'w'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
