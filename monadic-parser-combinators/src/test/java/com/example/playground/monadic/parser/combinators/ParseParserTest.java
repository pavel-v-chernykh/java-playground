package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParseParserTest {
    @Test
    public void shouldDiscardSpacesAndParseItem() {
        Parser parser = Parsers.parse(Parsers.integer());

        assertThat(parser.parse(" \t\r\n1"), is(equalTo(parsedList(1L, ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
