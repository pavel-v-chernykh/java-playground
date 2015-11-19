package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UpperParserTest {
    @Test
    public void shouldParseOnlyUpperCaseItem() {
        final Parser parser = Parsers.upper();

        assertThat(parser.parse("World"), is(equalTo(parserResultList("W", "orld"))));
        assertThat(parser.parse("world"), is(equalTo(parserResultList())));
        assertThat(parser.parse(""), is(equalTo(parserResultList())));
    }
}
