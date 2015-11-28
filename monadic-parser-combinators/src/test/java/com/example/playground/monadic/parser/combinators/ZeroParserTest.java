package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroParserTest {
    @Test
    public void shouldAlwaysFail() {
        final Parser parser = Parsers.error("Nothing to parse");

        assertThat(parser.parse("word"), is(equalTo(parsedError("Nothing to parse"))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Nothing to parse"))));
    }
}
