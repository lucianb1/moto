package ro.motorzz.repository.rowmapper;


import org.springframework.jdbc.core.RowMapper;
import ro.motorzz.model.token.authentication.AuthenticationToken;
import ro.motorzz.model.token.authentication.AuthenticationTokenBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationTokenRowMapper implements RowMapper<AuthenticationToken> {

    @Override
    public AuthenticationToken mapRow(ResultSet rs, int i) throws SQLException {
       return new AuthenticationTokenBuilder()
                .setToken(rs.getString("token"))
                .setExpiresOn(rs.getTimestamp("expires_on").toLocalDateTime())
                .setAccountID(rs.getInt("account_id"))
                .build();
    }
}
