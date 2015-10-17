package com.example.playground.result;

import org.junit.Test;

import java.util.Optional;

import static com.example.playground.result.Result.error;
import static com.example.playground.result.Result.result;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultTest {
    @Test
    public void getErrorShouldReturnEmptyErrorForResultVariant() {
        assertThat(result(1).getError(), is(Optional.empty()));
    }

    @Test
    public void getErrorShouldReturnNonEmptyErrorForErrorVariant() {
        assertThat(error(1).getError(), is(Optional.of(1)));
    }

    @Test
    public void getResultShouldReturnEmptyResultForErrorVariant() {
        assertThat(error(1).getResult(), is(Optional.empty()));
    }

    @Test
    public void getResultShouldReturnNonEmptyResultForResultVariant() {
        assertThat(result(1).getResult(), is(Optional.of(1)));
    }

    @Test
    public void isErrorShouldReturnTrueForErrorVariant() {
        assertThat(error(1).isError(), is(true));
    }

    @Test
    public void isErrorShouldReturnFalseForResultVariant() {
        assertThat(result(1).isError(), is(false));
    }

    @Test
    public void isResultShouldReturnTrueForResultVariant() {
        assertThat(result(1).isResult(), is(true));
    }

    @Test
    public void isResultShouldReturnFalseForErrorVariant() {
        assertThat(error(1).isResult(), is(false));
    }

    @Test
    public void flatMapShouldReturnMappedResultForResultVariant() {
        assertThat(result(1).flatMap(i -> result(i * 2)), is(result(2)));
        assertThat(result(1).flatMap(i -> error(i * 2)), is(error(2)));
    }

    @Test
    public void flatMapShouldReturnOriginalResultForErrorVariant() {
        assertThat(Result.<Integer, Integer>error(1).flatMap(i -> result(i * 2)), is(error(1)));
        assertThat(Result.<Integer, Integer>error(1).flatMap(i -> error(i * 2)), is(error(1)));
    }

    @Test
    public void flatMapErrorShouldReturnMappedErrorForErrorVariant() {
        assertThat(error(1).flatMapError(i -> result(i * 2)), is(result(2)));
        assertThat(error(1).flatMapError(i -> error(i * 2)), is(error(2)));
    }

    @Test
    public void flatMapErrorShouldReturnOriginalResultForResultVariant() {
        assertThat(Result.<Integer, Integer>result(1).flatMapError(i -> result(i * 2)), is(result(1)));
        assertThat(Result.<Integer, Integer>result(1).flatMapError(i -> error(i * 2)), is(result(1)));
    }

    @Test
    public void mapShouldReturnMappedResultForResultVariant() {
        assertThat(result(1).map(i -> i * 2), is(result(2)));
    }

    @Test
    public void mapShouldReturnOriginalErrorForErrorVariant() {
        assertThat(Result.<Integer, Integer>error(1).map(i -> i * 2), is(error(1)));
    }

    @Test
    public void mapErrorShouldReturnMappedErrorForErrorVariant() {
        assertThat(error(1).mapError(i -> i * 2), is(error(2)));
    }

    @Test
    public void mapErrorShouldReturnOriginalResultForResultVariant() {
        assertThat(Result.<Integer, Integer>result(1).mapError(i -> i * 2), is(result(1)));
    }

    @Test
    public void andElseShouldReturnOtherResultForResultVariant() {
        assertThat(result(1).andElse(result(2)), is(result(2)));
        assertThat(result(1).andElse(error(2)), is(error(2)));
    }

    @Test
    public void andElseShouldReturnOriginalResultForErrorVariant() {
        assertThat(error(1).andElse(result(2)), is(error(1)));
        assertThat(error(1).andElse(error(2)), is(error(1)));
    }

    @Test
    public void orElseShouldReturnOriginalResultForResultVariant() {
        assertThat(result(1).orElse(result(2)), is(result(1)));
        assertThat(result(1).orElse(error(2)), is(result(1)));
    }

    @Test
    public void orElseShouldReturnOtherResultForErrorVariant() {
        assertThat(error(1).orElse(result(2)), is(result(2)));
        assertThat(error(1).orElse(error(2)), is(error(2)));
    }
}
