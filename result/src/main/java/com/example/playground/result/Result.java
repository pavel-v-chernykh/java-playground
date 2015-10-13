package com.example.playground.result;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public boolean isError() {
        return error != null;
    }

    public boolean isResult() {
        return result == null;
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
