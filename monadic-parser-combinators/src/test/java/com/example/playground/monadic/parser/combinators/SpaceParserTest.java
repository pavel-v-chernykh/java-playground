package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpaceParserTest {
    @Test
    public void shouldParseSpace() {
        Parser parser = Parsers.space();

        assertThat(parser.parse(" "), is(equalTo(Parser.result(" ", ""))));
        assertThat(parser.parse("\n"), is(equalTo(Parser.result("\n", ""))));
        assertThat(parser.parse("\t"), is(equalTo(Parser.result("\t", ""))));
        assertThat(parser.parse("1"), is(equalTo(Parser.error("Predicate is not satisfied with '1'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
