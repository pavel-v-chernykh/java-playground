package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.List;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemParserTest {
    @Test
    public void shouldReturnFirstItemFromInputAndTheRestOfInput() {
        final String input = "word";
        final String result = "w";
        final String stream = "ord";
        final Parser parser = Parsers.item();

        final List<ParserResult> actualResults = parser.parse(input);
        final ParserResult actualParserResult = actualResults.get(0);
        final String actualResult = actualParserResult.getResult();
        final String actualStream = actualParserResult.getStream();

        final List<ParserResult> expectedResults = parserResultList(result, stream);
        final ParserResult expectedParserResult = expectedResults.get(0);
        final String expectedResult = expectedParserResult.getResult();
        final String expectedStream = expectedParserResult.getStream();

        assertThat(actualResult, is(equalTo(expectedResult)));
        assertThat(actualStream, is(equalTo(expectedStream)));
    }
}
