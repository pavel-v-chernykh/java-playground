package com.example.playground.pattern.matching;

import org.junit.Test;

import java.util.Optional;

import static java.util.function.Predicate.isEqual;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PatternMatchingTest {
    @Test
    public void shouldReturnFirstMatched() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(isEqual(1)).then(1)
                .when(isEqual(2)).then(2)
                .when(isEqual(3)).then(3);
        assertThat(patternMatching.apply(2), is(Optional.of(2)));
    }

    @Test
    public void shouldReturnEmptyIfNothingMatchedWithoutOtherwise() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(isEqual(1)).then(1)
                .when(isEqual(2)).then(2)
                .when(isEqual(3)).then(3);
        assertThat(patternMatching.apply(5), is(Optional.empty()));
    }

    @Test
    public void shouldReturnOtherwiseIfNothingMatched() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(isEqual(1)).then(1)
                .when(isEqual(2)).then(2)
                .when(isEqual(3)).then(3)
                .otherwise(4);
        assertThat(patternMatching.apply(5), is(Optional.of(4)));
    }
}
