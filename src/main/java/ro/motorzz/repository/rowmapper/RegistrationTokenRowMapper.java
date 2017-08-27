package ro.motorzz.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.motorzz.model.token.RegistrationToken;
import ro.motorzz.model.token.RegistrationTokenBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationTokenRowMapper implements RowMapper<RegistrationToken> {

    @Override
    public RegistrationToken mapRow(ResultSet rs, int i) throws SQLException {
       return new RegistrationTokenBuilder()
                .setToken(rs.getString("token"))
                .setExpiresOn(rs.getTimestamp("expires_on").toLocalDateTime())
                .setAccountID(rs.getInt("account_id"))
                .build();
    }
}
