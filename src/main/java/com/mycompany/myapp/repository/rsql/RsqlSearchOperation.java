package com.mycompany.myapp.repository.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

import java.util.Arrays;

/**
 * Created by Frank on 7/21/2017.
 * http://www.baeldung.com/spring-rest-api-query-search-language-tutorial
 */
public enum RsqlSearchOperation {
    EQUAL(RSQLOperators.EQUAL), NOT_EQUAL(RSQLOperators.NOT_EQUAL), GREATER_THAN(RSQLOperators.GREATER_THAN), GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL), LESS_THAN(RSQLOperators.LESS_THAN), LESS_THAN_OR_EQUAL(
        RSQLOperators.LESS_THAN_OR_EQUAL), IN(RSQLOperators.IN), NOT_IN(RSQLOperators.NOT_IN);

    private ComparisonOperator operator;

    RsqlSearchOperation(final ComparisonOperator operator) {
        this.operator = operator;
    }

    public static RsqlSearchOperation getSimpleOperator(final ComparisonOperator operator) {
        return Arrays.stream(values())
            .filter(operation -> operation.getOperator() == operator)
            .findAny().orElse(null);
    }

    public ComparisonOperator getOperator() {
        return operator;
    }
}
