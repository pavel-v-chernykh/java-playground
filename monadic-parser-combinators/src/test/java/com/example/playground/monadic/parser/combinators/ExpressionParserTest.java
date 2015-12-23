package com.example.playground.monadic.parser.combinators;

import com.example.playground.result.Result;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.playground.monadic.parser.combinators.Parsers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

interface ExpressionVisitor {
    void visit(Identifier identifier);

    void visit(Operator operator);

    void visit(BoolExpression expression);
}

interface Expression {
    void accept(ExpressionVisitor visitor);
}

@Value
class Identifier implements Expression {
    private final String id;

    public static Identifier of(String id) {
        return new Identifier(id);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

@Value
class Operator implements Expression {
    private final String operator;

    public static Operator of(String operator) {
        return new Operator(operator);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

@Value
class BoolExpression implements Expression {
    private final Expression left;
    private final Expression operator;
    private final Expression right;

    public static BoolExpression of(Expression left, Expression operator, Expression right) {
        return new BoolExpression(left, operator, right);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class ExpressionEvaluatingVisitor implements ExpressionVisitor {
    private final Map<Identifier, Boolean> context = new HashMap<>();
    private boolean evaluationResult;
    private String operator;

    public ExpressionEvaluatingVisitor(Map<Identifier, Boolean> context) {
        this.context.putAll(context);
    }

    @Override
    public void visit(Identifier identifier) {
        evaluationResult = context.get(identifier);
    }

    @Override
    public void visit(Operator operator) {
        this.operator = operator.getOperator();
    }

    @Override
    public void visit(BoolExpression expression) {
        expression.getLeft().accept(this);
        boolean left = evaluationResult;
        expression.getRight().accept(this);
        boolean right = evaluationResult;
        expression.getOperator().accept(this);
        switch (operator) {
            case "and":
                evaluationResult = left && right;
                break;
            case "or":
                evaluationResult = left || right;
                break;
        }
    }

    public boolean getEvaluationResult() {
        return evaluationResult;
    }
}

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ExpressionParsers {
    public static final Parser<Expression> OPERATOR = or(operator("or"), operator("and"));
    public static final Parser<Expression> IDENTIFIER = identifier();
    public static final Parser<Expression> BOOL_EXPRESSION = new Parser<Expression>() {
        @Override
        public Result<Parsed<Expression>, String> parse(String input) {
            final Parser<Expression> left = or(IDENTIFIER, BOOL_EXPRESSION_IN_PARENTHESIS, BOOL_EXPRESSION);
            final Parser<Expression> right = or(BOOL_EXPRESSION, BOOL_EXPRESSION_IN_PARENTHESIS, IDENTIFIER);
            return ternary(left, OPERATOR, right, ExpressionParsers::boolExpression).parse(input);
        }
    };
    public static final Parser<Expression> BOOL_EXPRESSION_IN_PARENTHESIS = inParenthesis(BOOL_EXPRESSION);
    public static final Parser<Expression> ROOT = parse(BOOL_EXPRESSION);

    public static Parser<Expression> identifier() {
        return token(bind(seq(alnum()), id -> result(Identifier.of(id))));
    }

    public static Parser<Expression> operator(String operator) {
        return token(bind(string(operator), o -> result(Operator.of(operator))));
    }

    public static Parser<Expression> boolExpression(Expression left, Expression operator, Expression right) {
        return result(BoolExpression.of(left, operator, right));
    }

    public static Parser<Expression> inParenthesis(Parser<Expression> expression) {
        return bracket(token(exact("(")), expression, token(exact(")")));
    }
}


public class ExpressionParserTest {
    @Test
    public void parseBooleanExpressionWithTrailingParenthesisExpression() {
        final BoolExpression parsedBoolExpression =
                BoolExpression.of(
                        Identifier.of("e1"),
                        Operator.of("and"),
                        BoolExpression.of(
                                Identifier.of("e2"),
                                Operator.of("and"),
                                BoolExpression.of(
                                        Identifier.of("e3"),
                                        Operator.of("or"),
                                        Identifier.of("e4")
                                )));
        final HashMap<Identifier, Boolean> context = new HashMap<>();
        context.put(Identifier.of("e1"), true);
        context.put(Identifier.of("e2"), true);
        context.put(Identifier.of("e3"), false);
        context.put(Identifier.of("e4"), true);
        final ExpressionEvaluatingVisitor visitor = new ExpressionEvaluatingVisitor(context);
        visitor.visit(parsedBoolExpression);
        assertThat(visitor.getEvaluationResult(), is(true));
        assertThat(ExpressionParsers.ROOT.parse("e1 and e2 and (e3 or e4)"), is(equalTo(Parser.result(parsedBoolExpression, ""))));
    }

    @Test
    public void parseBooleanExpressionWithParenthesisExpressionInTheMiddle() {
        final BoolExpression parsedBoolExpression =
                BoolExpression.of(
                        Identifier.of("e1"),
                        Operator.of("and"),
                        BoolExpression.of(
                                BoolExpression.of(
                                        Identifier.of("e2"),
                                        Operator.of("or"),
                                        Identifier.of("e3")
                                ),
                                Operator.of("and"),
                                Identifier.of("e4")
                        ));
        final HashMap<Identifier, Boolean> context = new HashMap<>();
        context.put(Identifier.of("e1"), true);
        context.put(Identifier.of("e2"), false);
        context.put(Identifier.of("e3"), false);
        context.put(Identifier.of("e4"), true);
        final ExpressionEvaluatingVisitor visitor = new ExpressionEvaluatingVisitor(context);
        visitor.visit(parsedBoolExpression);
        assertThat(visitor.getEvaluationResult(), is(false));
        assertThat(ExpressionParsers.ROOT.parse("e1 and (e2 or e3) and e4"), is(equalTo(Parser.result(parsedBoolExpression, ""))));

    }

    @Test
    public void parseBooleanExpressionWithLeadingParenthesisExpression() {
        final BoolExpression parsedBoolExpression =
                BoolExpression.of(
                        BoolExpression.of(
                                Identifier.of("e1"),
                                Operator.of("or"),
                                Identifier.of("e2")
                        ),
                        Operator.of("and"),
                        BoolExpression.of(
                                Identifier.of("e3"),
                                Operator.of("and"),
                                Identifier.of("e4")
                        ));
        final HashMap<Identifier, Boolean> context = new HashMap<>();
        context.put(Identifier.of("e1"), true);
        context.put(Identifier.of("e2"), false);
        context.put(Identifier.of("e3"), true);
        context.put(Identifier.of("e4"), true);
        final ExpressionEvaluatingVisitor visitor = new ExpressionEvaluatingVisitor(context);
        visitor.visit(parsedBoolExpression);
        assertThat(visitor.getEvaluationResult(), is(true));
        assertThat(ExpressionParsers.ROOT.parse("(e1 or e2) and e3 and e4"), is(equalTo(Parser.result(parsedBoolExpression, ""))));
    }
}
