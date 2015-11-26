package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.Arrays;

import static com.example.playground.monadic.parser.combinators.Parsed.parsedList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ManyParserTest {
    @Test
    public void shouldParseAllItemParsedByParamParser() {
        Parser parser = Parsers.many(Parsers.letter());

        assertThat(parser.parse("world"), is(equalTo(parsedList(Arrays.asList("w","o","r","l","d"), ""))));
        assertThat(parser.parse("World"), is(equalTo(parsedList(Arrays.asList("W","o","r","l","d"), ""))));
        assertThat(parser.parse("1world"), is(equalTo(parsedList())));
    }
}
