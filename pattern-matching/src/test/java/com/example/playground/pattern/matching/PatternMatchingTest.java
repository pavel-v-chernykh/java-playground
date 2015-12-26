package com.example.playground.pattern.matching;

import org.junit.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PatternMatchingTest {
    public static <T, R> Function<T, R> returnAlways(R r) {
        return t -> r;
    }

    public static <T> Predicate<T> equalTo(T t) {
        return isEqual(t);
    }

    @Test
    public void shouldReturnFirstMatched() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(equalTo(1)).then(returnAlways(1))
                .when(equalTo(2)).then(returnAlways(2))
                .when(equalTo(3)).then(returnAlways(3));
        assertThat(patternMatching.apply(2), is(Optional.of(2)));
    }

    @Test
    public void shouldReturnEmptyIfNothingMatchedWithoutOtherwise() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(equalTo(1)).then(returnAlways(1))
                .when(equalTo(2)).then(returnAlways(2))
                .when(equalTo(3)).then(returnAlways(3));
        assertThat(patternMatching.apply(5), is(Optional.empty()));
    }

    @Test
    public void shouldReturnOtherwiseIfNothingMatched() {
        final PatternMatching<Integer, Integer> patternMatching = PatternMatching.<Integer, Integer>builder()
                .when(equalTo(1)).then(returnAlways(1))
                .when(equalTo(2)).then(returnAlways(2))
                .when(equalTo(3)).then(returnAlways(3))
                .otherwise(returnAlways(4));
        assertThat(patternMatching.apply(5), is(Optional.of(4)));
    }

    @Test
    public void chainMatchingWithOptional() {
        final String someString = "some string";
        final Optional<String> result = PatternMatching.<String, String>builder()
                .when(Objects::isNull).then(returnAlways(""))
                .otherwise(identity())
                .apply(someString)
                .map((s) -> "%" + someString + "%");
        assertThat(result, is(Optional.of("%some string%")));
    }
}
