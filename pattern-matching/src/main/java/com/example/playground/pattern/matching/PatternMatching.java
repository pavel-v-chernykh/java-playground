package com.example.playground.pattern.matching;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternMatching<T, U> implements Function<T, Optional<U>> {
    private final Collection<Case> cases = new ArrayList<>();

    public static <T, U> PatternMatching<T, U> builder() {
        return new PatternMatching<>();
    }

    @Override
    public Optional<U> apply(final T t) {
        return cases.stream()
                .filter(c -> c.when.test(t))
                .findFirst()
                .map(c -> c.then.apply(t));
    }

    public When when(@NotNull Predicate<? super T> when) {
        return new When(when);
    }

    public PatternMatching<T, U> otherwise(final Function<T, U> f) {
        return new When(t -> true).then(f);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public final class When {
        private final Predicate<? super T> when;

        public PatternMatching<T, U> then(@NotNull final Function<T, U> f) {
            cases.add(new Case(when, f));
            return PatternMatching.this;
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private final class Case {
        private final Predicate<? super T> when;
        private final Function<T, U> then;
    }
}
