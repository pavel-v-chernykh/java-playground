package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SepbyParserTest {
    @Test
    public void shouldParseSeparatedItems() {
        Parser parser = Parsers.sepby(Parsers.integer(), Parsers.exact(","));

        assertThat(parser.parse("1"), is(equalTo(Parser.result(Collections.singletonList(1L), ""))));
        assertThat(parser.parse("0,1,2,3"), is(equalTo(Parser.result(Arrays.asList(0L, 1L, 2L, 3L), ""))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
