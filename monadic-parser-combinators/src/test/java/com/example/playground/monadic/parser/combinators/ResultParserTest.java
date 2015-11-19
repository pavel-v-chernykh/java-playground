package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultParserTest {
    @Test
    public void shouldNotConsumeInput() {
        final Parser parser = Parsers.result("1");

        assertThat(parser.parse("word"), is(equalTo(parserResultList("1", "word"))));
        assertThat(parser.parse(""), is(equalTo(parserResultList("1", ""))));
    }
}
