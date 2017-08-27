package ro.motorzz.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountBuilder;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.account.AccountType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int i) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getInt("id"))
                .setStatus(AccountStatus.valueFromString(rs.getString("status")))
                .setType(AccountType.valueFromString(rs.getString("type")))
                .setPassword(rs.getString("password"))
                .setEmail(rs.getString("email"))
                .setLoginTimes(rs.getInt("login_times"))
                .build();
    }
}
