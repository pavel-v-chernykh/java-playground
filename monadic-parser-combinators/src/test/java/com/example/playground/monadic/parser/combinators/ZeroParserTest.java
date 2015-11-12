package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroParserTest {
    @Test
    public void shouldAlwaysReturnEmptyList() {
        final Parser parser = Parsers.zero();

        assertThat(parser.parse("word"), is(equalTo(parserResultList())));
    }
}
