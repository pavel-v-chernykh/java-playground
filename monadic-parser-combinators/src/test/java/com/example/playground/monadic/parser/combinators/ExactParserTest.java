package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExactParserTest {
    @Test
    public void shouldParseExactChar() {
        final Parser parser = Parsers.exact("w");

        assertThat(parser.parse("world"), is(equalTo(parserResultList("w", "orld"))));
    }
}
