package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpaceParserTest {
    @Test
    public void shouldParseSpace() {
        Parser parser = Parsers.space();

        assertThat(parser.parse(" "), is(equalTo(parsedList(" ", ""))));
        assertThat(parser.parse("\n"), is(equalTo(parsedList("\n", ""))));
        assertThat(parser.parse("\t"), is(equalTo(parsedList("\t", ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedList())));
        assertThat(parser.parse(""), is(equalTo(parsedList())));
    }
}
