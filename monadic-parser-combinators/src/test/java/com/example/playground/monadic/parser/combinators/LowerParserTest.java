package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LowerParserTest {
    @Test
    public void shouldParseOnlyLowerCaseItem() {
        final Parser parser = Parsers.lower();

        assertThat(parser.parse("world"), is(equalTo(Parser.result("w", "orld"))));
        assertThat(parser.parse("World"), is(equalTo(Parser.error("Predicate is not satisfied with 'W'"))));
    }
}
