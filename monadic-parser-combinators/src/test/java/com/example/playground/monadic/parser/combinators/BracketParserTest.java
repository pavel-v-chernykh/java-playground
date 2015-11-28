package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Arrays;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BracketParserTest {
    @Test
    public void shouldParseIntegers() {
        Parser parser = Parsers.bracket(
                Parsers.exact("["),
                Parsers.sepby(Parsers.integer(), Parsers.exact(",")),
                Parsers.exact("]"));

        assertThat(parser.parse("[]"), is(equalTo(parsedError("Predicate is not satisfied with ']'"))));
        assertThat(parser.parse("[1]"), is(equalTo(parsedResult(Arrays.asList(1L), ""))));
        assertThat(parser.parse("[0,1,2,3]"), is(equalTo(parsedResult(Arrays.asList(0L, 1L, 2L, 3L), ""))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Can not parse item"))));
    }
}
