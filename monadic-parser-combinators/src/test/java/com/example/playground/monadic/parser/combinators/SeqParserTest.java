package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SeqParserTest {
    @Test
    public void shouldParseSequenceOfLetters() {
        Parser parser = Parsers.seq(Parsers.letter());

        assertThat(parser.parse("world"), is(equalTo(Parser.result("world", ""))));
        assertThat(parser.parse("World"), is(equalTo(Parser.result("World", ""))));
        assertThat(parser.parse("1world"), is(equalTo(Parser.error("Predicate is not satisfied with '1'"))));
    }
}
