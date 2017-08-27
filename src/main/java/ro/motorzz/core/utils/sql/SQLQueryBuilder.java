package ro.motorzz.core.utils.sql;

import java.util.*;
import java.util.stream.Collectors;

import static ro.motorzz.core.utils.sql.SQLConstants.*;


/**
 * Created by Luci on 31-Jul-17.
 */
public class SQLQueryBuilder {

    private static final String SUM_KEYWORD = "SUM";
    private static final String COUNT_KEYWORD = "COUNT";

    private final StringBuilder query = new StringBuilder();
    private final List<Object> params = new ArrayList<>();
    private StringJoiner currentClause;

    public SQLQueryBuilder append(String query) {

        if (query == null || query.isEmpty()) {
            return this;
        }
        if (currentClause != null) {
            currentClause.add(query);
        } else {
            this.query.append(query);
        }
        return this;
    }

    public SQLQueryBuilder append(SQLQuery query) {
        if (query == null) {
            return this;
        }
        return this.append(query.getQuery(), query.getParams());
    }

    public SQLQueryBuilder append(String query, Object... params) {
        this.append(query);
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder appendParam(Object... params) {
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder appendParams(List<Object> params) {
        this.params.addAll(params);
        return this;
    }

    public SQLQueryBuilder select() {
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER));
        this.query.append(SQLConstants.SELECT_KEYWORD);
        return this;
    }

    public SQLQueryBuilder delete(String tableName) {
        return this.append(SQLConstants.DELETE_KEYWORD + tableName);
    }

    public SQLQueryBuilder update(String tableName) {
        return this.append(SQLConstants.UPDATE_KEYWORD + tableName);
    }

    public SQLQueryBuilder selectDistinct() {
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER));
        this.query.append(SQLConstants.SELECT_KEYWORD + SQLConstants.DISTINCT_KEYWORD);
        return this;
    }

    public SQLQueryBuilder insertInto(String tableName) {
        this.query.append(SQLConstants.INSERT_KEYWORD);
        this.query.append(tableName);
        this.query.append(SQLConstants.EMPTY_SPACE);
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER, OPEN_BRACKET, CLOSE_BRACKET));
        return this;
    }

    public SQLQueryBuilder insertIntoIgnore(String tableName) {
        this.query.append(SQLConstants.INSERT_IGNORE_KEYWORD);
        this.query.append(tableName);
        this.query.append(SQLConstants.EMPTY_SPACE);
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER, OPEN_BRACKET, CLOSE_BRACKET));
        return this;
    }

    public SQLQueryBuilder replaceInto(String tableName) {
        this.query.append(SQLConstants.REPLACE_KEYWORD);
        this.query.append(tableName);
        this.query.append(SQLConstants.EMPTY_SPACE);
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER, OPEN_BRACKET, CLOSE_BRACKET));
        return this;
    }

    public SQLQueryBuilder values() {
        this.close();
        this.query.append(SQLConstants.VALUES_KEYWORD);
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER, OPEN_BRACKET, CLOSE_BRACKET));
        return this;
    }

    public SQLQueryBuilder set() {
        this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER));
        this.query.append(SQLConstants.SET_KEYWORD);
        return this;
    }

    public SQLQueryBuilder equal(String column, String value, Object... params) {
        this.append(column + SQLConstants.EQUAL_KEYWORD + value);
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQueryBuilder column(String column) {
        return this.append(column);
    }

    public SQLQueryBuilder columns(String... columns) {
        for (String value : columns) {
            this.append(value);
        }
        return this;
    }

    public SQLQueryBuilder columns(List<String> columns) {
        for (String value : columns) {
            this.append(value);
        }
        return this;
    }

    public SQLQueryBuilder column(String column, String alias) {
        if (Objects.isNull(alias)) {
            this.append(column);
        } else {
            this.append(column + AS_KEYWORD + alias);
        }
        return this;
    }

    public SQLQueryBuilder tableColumn(String table, String column) {
        return this.column(getColumnName(table, column));
    }

    public SQLQueryBuilder tableColumn(String table, String column, String alias) {
        return this.column(getColumnName(table, column), alias);
    }

    public SQLQueryBuilder sum(String args, String alias) {
        return this.columnFunction(SUM_KEYWORD, args, alias);
    }

    public SQLQueryBuilder count(String args) {
        return this.columnFunction(COUNT_KEYWORD, args, null);
    }

    public SQLQueryBuilder count(String args, String alias) {
        return this.columnFunction(COUNT_KEYWORD, args, alias);
    }

    public SQLQueryBuilder columnFunction(String function, String value, String alias) {
        return this.column(function + "(" + value + ")", alias);
    }

    public SQLQueryBuilder from(String table) {
        this.close();
        this.append(SQLConstants.FROM_KEYWORD + table);
        return this;
    }

    public SQLQueryBuilder from(String table, String alias) {
        this.close();
        this.append(SQLConstants.FROM_KEYWORD + table + " " + alias);
        return this;
    }

    public SQLQueryBuilder from(SQLQuery table) {
        this.close();
        this.append(SQLConstants.FROM_KEYWORD + table.getQuery(), table.getParams());
        return this;
    }

    public SQLQueryBuilder from(SQLQuery table, String alias) {
        this.close();
        this.append(SQLConstants.FROM_KEYWORD + "(" + table.getQuery() + ")" + AS_KEYWORD + alias, table.getParams());
        return this;
    }

    public SQLQueryBuilder innerJoin(String tableName) {
        this.close();
        this.append(SQLConstants.INNER_JOIN + tableName);
        return this;
    }

    public SQLQueryBuilder innerJoin(String tableName, String alias) {
        this.innerJoin(tableName);
        this.append(AS_KEYWORD + alias);
        return this;
    }

    public SQLQueryBuilder innerJoinOn(String tableName) {
        this.innerJoin(tableName);
        this.append(ON_KEYWORD);
        return this;
    }

    public SQLQueryBuilder innerJoinOn(String tableName, String alias) {
        this.innerJoin(tableName, alias);
        this.append(ON_KEYWORD);
        return this;
    }

    public SQLQueryBuilder leftJoin(String tableName) {
        this.close();
        this.append(SQLConstants.LEFT_JOIN + tableName);
        return this;
    }

    public SQLQueryBuilder leftJoin(String tableName, String alias) {
        this.leftJoin(tableName);
        this.append(AS_KEYWORD + alias);
        return this;
    }

    public SQLQueryBuilder leftJoinOn(String tableName) {
        this.leftJoin(tableName);
        this.append(ON_KEYWORD);
        return this;
    }

    public SQLQueryBuilder leftJoinOn(String tableName, String alias) {
        this.leftJoin(tableName, alias);
        this.append(ON_KEYWORD);
        return this;
    }

    public SQLQueryBuilder as(String alias) {
        this.append(AS_KEYWORD + alias);
        return this;
    }

    public SQLQueryBuilder where() {
        this.close();
        this.append(SQLConstants.WHERE_KEYWORD);
        return this;
    }

    public SQLQueryBuilder open(StringJoiner joiner) {
        this.close();
        this.currentClause = joiner;
        return this;
    }

    public SQLQueryBuilder close() {
        if (this.currentClause != null) {
            this.query.append(this.currentClause.toString());
            this.currentClause = null;
        }
        return this;
    }

    public SQLQueryBuilder in(String columnName, Collection<? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }
        query.append(columnName);
        return this.in(params);
    }

    public SQLQueryBuilder in(Collection<?> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }
        query.append(SQLConstants.IN_KEYWORD)
                .append(params.stream()
                        .peek(this.params::add)
                        .map(e -> SQLConstants.PARAM_KEYWORD)
                        .collect(Collectors.joining(SQLConstants.COMMA_DELIMITER, "(", ")"))
                )
                .append(SQLConstants.EMPTY_SPACE);
        return this;
    }

    /**
     * If the value is blank, no statement is appended.
     */
    public SQLQueryBuilder ilike(String column, String value) {
        if (value == null || value.trim().length() == 0) {
            return this;
        }
        String firstParam = UPPER_KEYWORD + OPEN_BRACKET + column + CLOSE_BRACKET;
        String secondParam = UPPER_KEYWORD + OPEN_BRACKET + "\"" + value + "\"" + CLOSE_BRACKET;
        return this.append(firstParam + LIKE_KEYWORD + secondParam);
    }

    public SQLQueryBuilder inlineIN(String columnName, Collection<? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }
        query.append(columnName);
        return this.inlineIN(params);
    }

    public SQLQueryBuilder inlineIN(Collection<? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }
        query.append(SQLConstants.IN_KEYWORD)
                .append(params.stream()
                        .map(e -> {
                            if (e instanceof String) {
                                return "'" + e + "'";
                            } else {
                                return e.toString();
                            }
                        })
                        .collect(Collectors.joining(SQLConstants.COMMA_DELIMITER, "(", ")"))
                );
        return this;
    }

    public SQLQueryBuilder optionalAppend(String prefix, String query) {
        if (query == null || query.isEmpty()) {
            return this;
        }
        this.append(prefix + query);
        return this;
    }

    public SQLQueryBuilder conditionalAppend(boolean condition, String query) {
        if (condition) {
            this.append(query);
        }
        return this;
    }

    public SQLQueryBuilder conditionalAppend(boolean condition, SQLQuery query) {
        if (condition) {
            this.append(query);
        }
        return this;
    }

    public SQLQueryBuilder optionalAppend(String prefix, SQLQuery query) {
        if (query == null || query.getQuery().isEmpty()) {
            return this;
        }
        this.optionalAppend(prefix, query.getQuery(), query.getParams());
        return this;
    }

    public SQLQueryBuilder optionalAppend(String prefix, String query, Object... params) {
        if (query == null || query.isEmpty()) {
            return this;
        }
        this.append(prefix + query);
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    public SQLQuery build() {
        this.close();
        return new SQLQuery(query.toString(), params.toArray());
    }

    public static String getColumnName(final String table, final String columnName) {
        return table + SQLConstants.DOT + columnName;
    }

    public SQLQueryBuilder onDuplicateKeyUpdate() {
        this.close();
        return this.append(SQLConstants.ON_DUPLICATE_KEY_UPDATE);
    }

    public SQLQueryBuilder alterTable(String table) {
        this.append(SQLConstants.ALTER_TABLE + table);
        return this.open(new StringJoiner(SQLConstants.COMMA_DELIMITER));
    }

    public SQLQueryBuilder dropColumn(String columnName) {
        return this.append(SQLConstants.DROP_COLUMN + columnName);
    }

    public SQLQueryBuilder addColumn(String columnDefinition) {
        return this.append(SQLConstants.ADD_COLUMN + columnDefinition);
    }

    public SQLQueryBuilder limit(int skip, int limit) {
        if (skip >= 0 && limit > 0) {
            return this.append(SQLConstants.LIMIT + skip + SQLConstants.COMMA_DELIMITER + limit);
        } else {
            return this;
        }
    }

}
