package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlnumParserTest {
    @Test
    public void shouldParseLetterOrDigitItem() {
        Parser parser = Parsers.alnum();

        assertThat(parser.parse("world"), is(equalTo(parsedList("w", "orld"))));
        assertThat(parser.parse("1world"), is(equalTo(parsedList("1", "world"))));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
