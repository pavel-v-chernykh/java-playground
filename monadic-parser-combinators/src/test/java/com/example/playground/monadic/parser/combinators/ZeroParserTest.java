package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroParserTest {
    @Test
    public void shouldAlwaysReturnEmptyList() {
        final Parser<Integer, Integer, IntStream> parser = Parsers.<Integer, Integer, IntStream>zero();
        final String input = "word";
        final List<ParserResult<Integer, Integer, IntStream>> actualParserResults = parser.parse(input.chars());

        assertThat(actualParserResults, is(equalTo(Collections.emptyList())));
    }
}
