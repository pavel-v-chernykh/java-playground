package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpaceParserTest {
    @Test
    public void shouldParseSpace() {
        Parser parser = Parsers.space();

        assertThat(parser.parse(" "), is(equalTo(parsedResult(" ", ""))));
        assertThat(parser.parse("\n"), is(equalTo(parsedResult("\n", ""))));
        assertThat(parser.parse("\t"), is(equalTo(parsedResult("\t", ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedError("Predicate is not satisfied with '1'"))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Can not parse item"))));
    }
}
