package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TernaryParserTest {
    @Test
    public void shouldParseIntegers() {
        Parser parser = Parsers.ternary(
                Parsers.exact("["),
                Parsers.sepby(Parsers.integer(), Parsers.exact(",")),
                Parsers.exact("]"),
                (s, longs, s2) -> Parsers.result(longs));

        assertThat(parser.parse("[]"), is(equalTo(Parser.error("Predicate is not satisfied with ']'"))));
        assertThat(parser.parse("[1]"), is(equalTo(Parser.result(Arrays.asList(1L), ""))));
        assertThat(parser.parse("[0,1,2,3]"), is(equalTo(Parser.result(Arrays.asList(0L, 1L, 2L, 3L), ""))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
