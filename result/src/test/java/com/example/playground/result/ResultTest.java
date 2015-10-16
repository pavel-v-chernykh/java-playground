package com.example.playground.result;

import org.junit.Test;

import java.util.Optional;

import static com.example.playground.result.Result.error;
import static com.example.playground.result.Result.result;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultTest {
    @Test
    public void getErrorShouldReturnEmptyErrorIfConstructedWithResult() {
        assertThat(result(1).getError(), is(Optional.empty()));
    }

    @Test
    public void getResultShouldReturnEmptyResultIfConstructedWithError() {
        assertThat(error(1).getResult(), is(Optional.empty()));
    }

    @Test
    public void getErrorShouldReturnOptionalErrorIfConstructedWithError() {
        assertThat(error(1).getError(), is(Optional.of(1)));
    }

    @Test
    public void getResultShouldReturnOptionalResultIfConstructedWithResult() {
        assertThat(result(1).getResult(), is(Optional.of(1)));
    }

    @Test
    public void isErrorShouldReturnTrueIfConstructedWithError() {
        assertThat(error(1).isError(), is(true));
    }

    @Test
    public void isResultShouldReturnFalseIfConstructedWithError() {
        assertThat(error(1).isResult(), is(false));
    }

    @Test
    public void isErrorShouldReturnFalseIfConstructedWithResult() {
        assertThat(result(1).isError(), is(false));
    }

    @Test
    public void isResultShouldReturnTrueIfConstructedWithResult() {
        assertThat(result(1).isResult(), is(true));
    }
}
