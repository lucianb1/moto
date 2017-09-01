package ro.motorzz.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.core.utils.sql.SQLQuery;
import ro.motorzz.core.utils.sql.SQLQueryBuilder;
import ro.motorzz.model.token.authentication.AuthenticationToken;
import ro.motorzz.repository.base.BaseRepository;
import ro.motorzz.repository.rowmapper.AuthenticationTokenRowMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AuthenticationTokenRepository extends BaseRepository {

    private static final AuthenticationTokenRowMapper authenticationTokenRowMapper = new AuthenticationTokenRowMapper();

    public boolean isTokenUnique(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().columns("token").from("authentication_tokens")
                .where().equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        List<String> tokens = jdbcTemplate.queryForList(query.getQuery(), query.getParams(), String.class);
        return tokens.isEmpty();
    }

    public AuthenticationToken saveAuthenticationToken(String token, LocalDateTime expiresOn, int accountID) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .insertInto("authentication_tokens")
                .columns("token", "expires_on", "account_id")
                .values()
                .append(":token", token)
                .append(":expires_on", expiresOn)
                .append(":account_id", accountID);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", token)
                .addValue("expires_on", expiresOn)
                .addValue("account_id", accountID);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(queryBuilder.build().getQuery(), params, keyHolder);
        return this.findByToken(token);
    }

    public AuthenticationToken findByToken(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().columns("token", "expires_on", "account_id")
                .from("authentication_tokens")
                .where().equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), authenticationTokenRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("AuthenticationToken by token not found: " + token);
        }
    }

    public void deleteAuthenticationToken(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .delete("authentication_tokens")
                .where()
                .equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        jdbcTemplate.update(query.getQuery(), query.getParams());
    }
}
