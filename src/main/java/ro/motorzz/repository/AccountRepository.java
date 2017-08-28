package ro.motorzz.repository;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.core.utils.sql.SQLQuery;
import ro.motorzz.core.utils.sql.SQLQueryBuilder;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.account.AccountType;
import ro.motorzz.repository.rowmapper.AccountRowMapper;

import java.util.List;

@Repository
public class AccountRepository extends BaseRepository {

    private static final AccountRowMapper accountRowMapper = new AccountRowMapper();

    public boolean isEmailUnique(String email) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().column("email")
                .from("accounts").where()
                .equal("email", "?", email);
        SQLQuery query = queryBuilder.build();
        List<String> emails = jdbcTemplate.queryForList(query.getQuery(), query.getParams(), String.class);
        return emails.isEmpty();
    }

    public Account findAccount(int id) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().append("*").from("accounts")
                .where().equal("id", "?", id);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), accountRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account by id not found: " + id);
        }
    }

    public Account findAccountByEmail(String email) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().append("*").from("accounts")
                .where().equal("email", "?", email);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), accountRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account by email not found: " + email);
        }
    }

    public Account saveAccount(String email, String password, AccountType type, AccountStatus status) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .insertInto("accounts")
                .columns("email", "password", "type", "status")
                .values()
                .append(":email", email)
                .append(":password", password)
                .append(":type", type.name())
                .append(":status", status.name());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password)
                .addValue("type", type.name())
                .addValue("status", status.name());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(queryBuilder.build().getQuery(), params, keyHolder);
        return this.findAccount(keyHolder.getKey().intValue());
    }


    public Account updateStatus(int id, AccountStatus accountStatus) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .update("accounts")
                .set()
                .equal("status", "?", accountStatus.name())
                .where()
                .equal("id", "?", id);
        SQLQuery query = queryBuilder.build();
        jdbcTemplate.update(query.getQuery(), query.getParams());
        return this.findAccount(id);
    }

}
