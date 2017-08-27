package ro.motorzz.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.motorzz.core.utils.sql.SQLQuery;
import ro.motorzz.core.utils.sql.SQLQueryBuilder;
import ro.motorzz.model.token.RegistrationToken;
import ro.motorzz.repository.rowmapper.RegistrationTokenRowMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RegistrationTokenRepository extends BaseRepository {

    private static final RegistrationTokenRowMapper registrationTokenRowMapper = new RegistrationTokenRowMapper();

    public RegistrationToken saveRegistrationToken(String token, LocalDateTime expiresOn, int accountID){
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .insertInto("registration_tokens")
                .columns("token","expires_on","account_id")
                .values()
                .append(":token",token)
                .append(":expires_on",expiresOn)
                .append(":account_id", accountID);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", token)
                .addValue("expires_on", expiresOn)
                .addValue("account_id", accountID);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(queryBuilder.build().getQuery(),params,keyHolder);
        return this.findByToken(token);

    }

    public RegistrationToken findByToken(String token){
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().columns("token","expires_on","account_id")
                .from("registration_tokens")
                .where().equal("token","?",token);
        SQLQuery query = queryBuilder.build();
        try {
            return jdbcTemplate.queryForObject(query.getQuery(), query.getParams(), registrationTokenRowMapper); //TODO better way
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public boolean isTokenUnique(String token){
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .select().columns("token").from("registration_tokens")
                .where().equal("token","?",token);
        SQLQuery query = queryBuilder.build();
        List<String> tokens = jdbcTemplate.queryForList(query.getQuery(), query.getParams(), String.class);
        if(tokens.isEmpty()){
            return true;
        }
        return false;

    }
}
