package ro.motorzz.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.motorzz.model.token.resetpassword.ResetPasswordBuilder;
import ro.motorzz.model.token.resetpassword.ResetPasswordToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordRowMapper implements RowMapper<ResetPasswordToken> {

    @Override
    public ResetPasswordToken mapRow(ResultSet rs, int i) throws SQLException {
        return new ResetPasswordBuilder()
                .setToken(rs.getString("token"))
                .setExpiresOn(rs.getTimestamp("expires_on").toLocalDateTime())
                .setPassword(rs.getString("password"))
                .setAccountID(rs.getInt("account_id"))
                .build();
    }
}
