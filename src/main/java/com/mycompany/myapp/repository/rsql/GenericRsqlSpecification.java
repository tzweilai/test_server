package com.mycompany.myapp.repository.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 7/21/2017.
 * http://www.baeldung.com/spring-rest-api-query-search-language-tutorial
 */
public class GenericRsqlSpecification<T> implements Specification<T> {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(final String property, final ComparisonOperator operator, final List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Object> args = castArguments(root);
        final Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

            case EQUAL: {
                if (argument instanceof String) {
                    return builder.like(root.get(property), argument.toString().replace('*', '%'));
                } else if (argument instanceof Boolean) {
                    return builder.equal(root.get(property), argument);
                } else if (argument instanceof Enum) {
                    return builder.equal(root.get(property), argument);
                } else if (argument == null) {
                    return builder.isNull(root.get(property));
                } else {
                    return builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(root.<String> get(property), argument.toString().replace('*', '%'));
                } else if (argument instanceof Boolean) {
                    return builder.notEqual(root.get(property), argument);
                } else if (argument instanceof Enum) {
                    return builder.notEqual(root.get(property), argument);
                } else if (argument == null) {
                    return builder.isNotNull(root.get(property));
                } else {
                    return builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                return builder.greaterThan(root.<String> get(property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                if (argument instanceof ZonedDateTime){
                    return builder.greaterThanOrEqualTo(root. get(property).as(ZonedDateTime.class), (ZonedDateTime)argument);
                } else {
                    return builder.greaterThanOrEqualTo(root.<String>  get(property), argument.toString());
                }
            }
            case LESS_THAN: {
                return builder.lessThan(root.<String> get(property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                if (argument instanceof ZonedDateTime){
                    return builder.lessThanOrEqualTo(root. get(property).as(ZonedDateTime.class), (ZonedDateTime)argument);
                } else {
                    return builder.lessThanOrEqualTo(root.<String>  get(property), argument.toString());
                }
            }
            case IN:
                return root.get(property).in(args);
            case NOT_IN:
                return builder.not(root.get(property).in(args));
        }

        return null;
    }

    // === private

    private List<Object> castArguments(final Root<T> root) {
        final List<Object> args = new ArrayList<Object>();
        final Class<? extends Object> type = root.get(property).getJavaType();

        for (final String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else if (type.equals(Float.class)) {
                args.add(Float.parseFloat(argument));
            } else if(type.equals(String.class)){
                args.add(argument);
            } else if(type.equals(ZonedDateTime.class)){
                args.add(Instant.ofEpochMilli(Long.parseLong(argument)).atZone(ZoneOffset.systemDefault()));
            } else if(type.equals(Boolean.class)){
                if(argument.equalsIgnoreCase("null")){
                    args.add(null);
                } else {
                    args.add(Boolean.parseBoolean(argument));
                }
            } else if(type.isEnum()){
                //https://stackoverflow.com/questions/17051495/how-to-convert-string-to-enum-value-when-enum-type-reference-is-a-class
                args.add(Enum.valueOf((Class<Enum>)type,  argument));
            }else {
                args.add(Long.parseLong(argument));
            }
        }

        return args;
    }

}
