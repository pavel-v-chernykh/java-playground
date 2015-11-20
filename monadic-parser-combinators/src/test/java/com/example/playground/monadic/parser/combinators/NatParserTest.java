package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NatParserTest {
    @Test
    public void shouldParseNaturalNumbers() {
        Parser parser = Parsers.nat();

        assertThat(parser.parse("0"), is(equalTo(parsedList("0", ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList("1", ""))));
        assertThat(parser.parse("0123456789"), is(equalTo(parsedList("0123456789", ""))));
        assertThat(parser.parse("-1"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
