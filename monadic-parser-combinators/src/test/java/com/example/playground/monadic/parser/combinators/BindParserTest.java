package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static com.example.playground.monadic.parser.combinators.Parsers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BindParserTest {
    @Test
    public void shouldBindTwoParsers() {
        final Parser parser = bind(item(), i1 -> bind(item(), i2 -> result(i1 + i2)));

        assertThat(parser.parse("world"), is(equalTo(parserResultList("wo", "rld"))));
    }
}
