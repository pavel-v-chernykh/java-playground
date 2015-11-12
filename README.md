# Java Playground

Occasional useful(less) java experiments

## [Pattern Matching][pattern-matching]

Implementation of pattern matching facility using 
[```Predicate```][predicate] and [lambdas][lambdas] features of Java 8.

## [Result][result]

Implement container object which allows to compose error processing.
Very close to [```Either```][either-monad] monad.

## [Monadic Parser Combinators][monadic-parser-combinators]

Monadic parser combinators implementation in Java 8. 
Inspired by the paper ["Monadic Parser Combinators"][monadic-parser-combinators-paper] 
written by Graham Hutton and Erik Meijer. 

[pattern-matching]: https://github.com/pavel-v-chernykh/java_playground/tree/master/pattern-matching
[result]: https://github.com/pavel-v-chernykh/java_playground/tree/master/result
[monadic-parser-combinators]: https://github.com/pavel-v-chernykh/java_playground/tree/master/monadic-parser-combinators

[predicate]: https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html
[lambdas]: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
[either-monad]: http://learnyouahaskell.com/for-a-few-monads-more#error
[monadic-parser-combinators-paper]: http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
