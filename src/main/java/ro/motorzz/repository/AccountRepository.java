package ro.motorzz.repository;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.motorzz.core.exception.InvalidArgumentException;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.model.account.Account;
import ro.motorzz.model.account.AccountStatus;
import ro.motorzz.model.account.AccountType;
import ro.motorzz.repository.rowmapper.AccountRowMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Repository
public class AccountRepository extends BaseRepository {

    private static final AccountRowMapper accountRowMapper = new AccountRowMapper();

    public Account saveAccount(String email, String password, AccountType type, AccountStatus status) {
        String sql = "INSERT INTO accounts (email, password, type, status) VALUES (:email, :password, :type, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password)
                .addValue("type", type.name())
                .addValue("status", status.name());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(sql, params, keyHolder);
        return this.findAccount(keyHolder.getKey().intValue());
    }

    //dev use
    public List<Account> getAll() {
        String sql = "SELECT * from accounts";
        return jdbcTemplate.query(sql, accountRowMapper);
    }

//    public List<Account> filterAccounts(PaginationRequest paginationRequest, AccountFilterRequest filter) {
//        int skip = paginationRequest.getPage() * paginationRequest.getPageSize();
//        int limit = paginationRequest.getPageSize();
//        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
//                .select().append("*").from("accounts")
//                .where().append("1 = 1");
//
//        if (Objects.nonNull(filter.getAccountID())) {
//            queryBuilder.append(" AND id = ?", filter.getAccountID());
//        }
//        if (Objects.nonNull(filter.getEmail())) {
//            queryBuilder.ilike("email", filter.getEmail());
//        }
//        if (Objects.nonNull(filter.getStatus())) {
//            queryBuilder.equal("status", "?", filter.getStatus().name());
//        }
//        if (Objects.nonNull(filter.getType())) {
//            queryBuilder.equal("type", "?", filter.getType().name());
//        }
//
//        queryBuilder.limit(skip, limit)
//                .append("ORDER BY created_on");
//        SQLQuery query = queryBuilder.build();
//
//        return jdbcTemplate.query(query.getQuery(), query.getParams(), accountRowMapper);
//    }

    public Account findAccount(int accountID) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        List<Account> foundAccounts = jdbcTemplate.query(sql, new Object[]{accountID}, accountRowMapper);
        if (foundAccounts.isEmpty()) {
            throw new NotFoundException("Account with id not found: " + accountID);
        } else {
            return foundAccounts.get(0);
        }
    }

    public Account findAccount(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?";
        List<Account> foundAccounts = jdbcTemplate.query(sql, new Object[]{email}, accountRowMapper);
        if (foundAccounts.isEmpty()) {
            throw new NotFoundException("Account with email not found: " + email);
        } else {
            return foundAccounts.get(0);
        }
    }


    public boolean accountWithEmailExists(String email) {
        String sql = "SELECT COUNT(id) FROM accounts WHERE email = ?";
        Long count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Long.class);
        return count > 0;
    }

    public Account deleteAccount(int accountID) {
        Account foundAccount = findAccount(accountID); // throws not found if the id is bad
        String sql = "DELETE FROM accounts WHERE id = ?";
        int deletedAccounts = jdbcTemplate.update(sql, accountID);
        if (deletedAccounts != 1) {
            throw new InternalException("The account should be deleted");
        }
        return foundAccount;
    }

    public Account updateAccountStatus(int accountID, AccountStatus newStatus) {
        String sql = "UPDATE accounts SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, newStatus.name(), accountID);
        return this.findAccount(accountID);
    }

    public Account updateAccountPassword(String email, String password) {
        String sql = "UPDATE accounts SET password = ? WHERE email = ?";
        jdbcTemplate.update(sql, password, email);
        return this.findAccount(email);
    }

    public boolean allAccountsExist(Integer... accountIDs) {
        if (accountIDs.length == 0) {
            throw new InvalidArgumentException("The accounts array cannot be empty");
        }
        String sql = "SELECT COUNT(id) FROM accounts WHERE id IN (:accountIDs)";
        MapSqlParameterSource params = new MapSqlParameterSource("accountIDs", new HashSet<>(Arrays.asList(accountIDs)));
        return namedJdbcTemplate.queryForObject(sql, params, Long.class) == accountIDs.length;
    }

    public void incrementAccountLoginTimes(int accountID) {
        String sql = "UPDATE accounts SET login_times = login_times + 1 WHERE id = ?";
        jdbcTemplate.update(sql, accountID);
    }

}
