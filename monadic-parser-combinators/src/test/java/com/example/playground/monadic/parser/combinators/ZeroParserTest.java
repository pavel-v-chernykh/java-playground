package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroParserTest {
    @Test
    public void shouldAlwaysReturnEmptyList() {
        final Parser parser = Parsers.zero();
        final String input = "word";
        final List<ParserResult> actualParserResults = parser.parse(input);

        assertThat(actualParserResults, is(equalTo(Collections.emptyList())));
    }
}
