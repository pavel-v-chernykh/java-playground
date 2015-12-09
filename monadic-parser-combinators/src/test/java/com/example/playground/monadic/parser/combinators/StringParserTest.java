package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StringParserTest {
    @Test
    public void shouldParseString() {
        Parser parser = Parsers.string("world");

        assertThat(parser.parse("world"), is(equalTo(Parser.result("world", ""))));
        assertThat(parser.parse("World"), is(equalTo(Parser.error("Can not parse string 'world'"))));
        assertThat(parser.parse("1world"), is(equalTo(Parser.error("Can not parse string 'world'"))));
    }
}
