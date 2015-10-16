package com.example.playground.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<R, E> {
    private R result;
    private E error;

    public static <R, E> Result<R, E> result(@NotNull R result) {
        return new Result<>(result, null);
    }

    public static <R, E> Result<R, E> error(@NotNull E error) {
        return new Result<>(null, error);
    }

    public Optional<R> getResult() {
        return Optional.ofNullable(result);
    }

    public Optional<E> getError() {
        return Optional.ofNullable(error);
    }

    public boolean isError() {
        return error != null && result == null;
    }

    public boolean isResult() {
        return result != null && error == null;
    }

    public <U> Result<U, E> flatMap(@NotNull Function<? super R, Result<U, E>> mapper) {
        return isResult() ? mapper.apply(result) : error(error);
    }

    public <U> Result<U, E> map(@NotNull Function<? super R, ? extends U> mapper) {
        return flatMap(r -> result(mapper.apply(r)));
    }

    public <U> Result<U, E> andElse(Result<U, E> other) {
        return isResult() ? other : error(error);
    }

    public <F> Result<R, F> orElse(Result<R, F> other) {
        return isError() ? other : result(result);
    }
}
