package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsers.sat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SatParserTest {
    @Test
    public void shouldParseOnlyItemSatisfyingPredicate() {
        final Parser parser = sat(s -> s.codePoints().allMatch(Character::isDigit));

        assertThat(parser.parse("1world"), is(equalTo(Parser.result("1", "world"))));
        assertThat(parser.parse("world"), is(equalTo(Parser.error("Predicate is not satisfied with 'w'"))));
        assertThat(parser.parse(""), is(equalTo(Parser.error("Can not parse item"))));
    }
}
