package com.example.playground.result;

import org.junit.Test;

import java.util.Optional;

import static com.example.playground.result.Result.error;
import static com.example.playground.result.Result.result;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultTest {
    @Test
    public void shouldReturnEmptyErrorIfConstructedWithResult() {
        assertThat(result(1).getError(), is(Optional.empty()));
    }

    @Test
    public void shouldReturnEmptyResultIfConstructedWithError() {
        assertThat(error(1).getResult(), is(Optional.empty()));
    }

    @Test
    public void shouldHaveOptionalErrorIfConstructedWithError() {
        assertThat(error(1).getError(), is(Optional.of(1)));
    }

    @Test
    public void shouldReturnOptionalResultIfConstructedWithResult() {
        assertThat(result(1).getResult(), is(Optional.of(1)));
    }
}
