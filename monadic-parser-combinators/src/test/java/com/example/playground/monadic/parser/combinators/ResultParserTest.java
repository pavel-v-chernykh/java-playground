package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultParserTest {
    @Test
    public void shouldReturnParserWhichReturnsListOfParserResultWithPassedValueAndUnconsumedInputStream() {
        final String result = "1";
        final String input = "word";
        final Parser parser = Parsers.<Integer, Integer, IntStream>result(result);

        final List<ParserResult> actualResults = parser.parse(input);
        final ParserResult actualParserResult = actualResults.get(0);
        final String actualResult = actualParserResult.getResult();
        final String actualStream = actualParserResult.getStream();

        final List<ParserResult> expectedResults = parserResultList(result, input);
        final ParserResult expectedParserResult = expectedResults.get(0);
        final String expectedResult = expectedParserResult.getResult();
        final String expectedStream = expectedParserResult.getStream();

        assertThat(actualResult, is(equalTo(expectedResult)));
        assertThat(actualStream, is(equalTo(expectedStream)));
    }
}
