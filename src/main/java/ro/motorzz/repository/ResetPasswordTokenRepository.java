package ro.motorzz.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.core.utils.sql.SQLQuery;
import ro.motorzz.core.utils.sql.SQLQueryBuilder;
import ro.motorzz.model.token.resetpassword.ResetPasswordToken;
import ro.motorzz.repository.base.BaseRepository;
import ro.motorzz.repository.rowmapper.ResetPasswordRowMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ResetPasswordTokenRepository extends BaseRepository {

    private static final ResetPasswordRowMapper registrationTokenRowMapper = new ResetPasswordRowMapper();

    public ResetPasswordToken saveResetPasswordToken(String token, LocalDateTime expiresOn,String password, int accountID) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .insertInto("reset_password_tokens")
                .columns("token", "expires_on", "password", "account_id")
                .values()
                .append(":token", token)
                .append(":expires_on", expiresOn)
                .append(":password", password)
                .append(":account_id", accountID);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", token)
                .addValue("expires_on", expiresOn)
                .addValue("password", password)
                .addValue("account_id", accountID);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(queryBuilder.build().getQuery(), params, keyHolder);
        return this.findByToken(token);

    }

    public boolean isTokenUnique(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().columns("token").from("reset_password_tokens")
                .where().equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        List<String> tokens = jdbcTemplate.queryForList(query.getQuery(), query.getParams(), String.class);
        return tokens.isEmpty();
    }

    public ResetPasswordToken findByToken(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().append("*").from("reset_password_tokens")
                .where().equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), registrationTokenRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("ResetPasswordToken by token not found: " + token);
        }
    }

    public ResetPasswordToken findResetPasswordToken(int id) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().append("*").from("reset_password_tokens")
                .where().equal("id", "?", id);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), registrationTokenRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("ResetPasswordToken by id not found: " + id);
        }
    }

    public void deleteResetPasswordToken(String token) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .delete("reset_password_tokens")
                .where()
                .equal("token", "?", token);
        SQLQuery query = queryBuilder.build();
        jdbcTemplate.update(query.getQuery(), query.getParams());
    }
}
