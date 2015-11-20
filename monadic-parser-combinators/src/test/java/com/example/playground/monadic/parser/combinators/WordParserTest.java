package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WordParserTest {
    @Test
    public void shouldParseManyLetters() {
        Parser parser = Parsers.word();

        assertThat(parser.parse("world"), is(equalTo(parsedList("world", ""))));
        assertThat(parser.parse("World"), is(equalTo(parsedList("World", ""))));
        assertThat(parser.parse("1world"), is(equalTo(parsedList())));
    }
}
