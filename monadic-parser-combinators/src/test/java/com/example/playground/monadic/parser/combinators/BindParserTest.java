package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedError;
import static com.example.playground.monadic.parser.combinators.Parsed.parsedResult;
import static com.example.playground.monadic.parser.combinators.Parsers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BindParserTest {
    @Test
    public void shouldBindParserAndParserFunction() {
        final Parser parser = bind(item(), i1 -> bind(item(), i2 -> result(i1 + i2)));

        assertThat(parser.parse("world"), is(equalTo(parsedResult("wo", "rld"))));
        assertThat(parser.parse("w"), is(equalTo(parsedError("Can not parse item"))));
        assertThat(parser.parse(""), is(equalTo(parsedError("Can not parse item"))));
    }
}
