package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpacesParserTest {
    @Test
    public void shouldParseSpaces() {
        Parser parser = Parsers.spaces();

        assertThat(parser.parse(" \t\n\r"), is(equalTo(Parser.result(" \t\n\r", ""))));
        assertThat(parser.parse("1"), is(equalTo(Parser.error("Predicate is not satisfied with '1'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
