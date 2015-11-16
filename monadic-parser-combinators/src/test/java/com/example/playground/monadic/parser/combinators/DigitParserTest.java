package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DigitParserTest {
    @Test
    public void shouldParseDigit() {
        final Parser parser = Parsers.digit();

        assertThat(parser.parse("1world"), is(equalTo(parserResultList("1", "world"))));
    }
}
