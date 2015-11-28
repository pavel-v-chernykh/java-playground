package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NatParserTest {
    @Test
    public void shouldParseNaturalNumbers() {
        Parser parser = Parsers.nat();

        assertThat(parser.parse("0"), is(equalTo(parsedResult(0L, ""))));
        assertThat(parser.parse("1"), is(equalTo(parsedResult(1L, ""))));
        assertThat(parser.parse("0123456789"), is(equalTo(parsedResult(123456789L, ""))));
        assertThat(parser.parse("-1"), is(equalTo(parsedError("Predicate is not satisfied with '-'"))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Can not parse item"))));
    }
}
