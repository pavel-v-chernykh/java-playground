package com.example.playground.monadic.parser.combinators;

@FunctionalInterface
public interface TriFunction<T1, T2, T3, T4> {
    T4 apply(T1 t1, T2 t2, T3 t3);
}
