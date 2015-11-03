package com.example.playground.monadic.parser.combinators;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.example.playground.monadic.parser.combinators.ParserResult.parserResultList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultParserTest {
    @Test
    public void shouldReturnParserWhichReturnsListOfParserResultWithPassedValueAndUnconsumedInputStream() {
        final int result = 1;
        final String input = "word";
        final Parser<Integer, Integer, IntStream> parser = Parsers.<Integer, Integer, IntStream>result(result);

        final List<ParserResult<Integer, Integer, IntStream>> actualResults = parser.parse(input.chars());
        final ParserResult<Integer, Integer, IntStream> actualParserResult = actualResults.get(0);
        final Integer actualResult = actualParserResult.getResult();
        final List<Integer> actualList = actualParserResult.getStream().boxed().collect(toList());

        final List<ParserResult<Integer, Integer, IntStream>> expectedResults = parserResultList(result, input.chars());
        final ParserResult<Integer, Integer, IntStream> expectedParserResult = expectedResults.get(0);
        final Integer expectedResult = expectedParserResult.getResult();
        final List<Integer> expectedList = expectedParserResult.getStream().boxed().collect(toList());

        assertThat(actualResult, is(equalTo(expectedResult)));
        assertThat(actualList, is(equalTo(expectedList)));
    }
}
