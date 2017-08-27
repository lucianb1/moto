package ro.motorzz.model.account;

public class Account {

    private final int id;
    private final String email;
    private final String password;
    private final AccountType type;
    private final AccountStatus status;
    private final int loginTimes;

    public Account(int id, String email, String password, AccountType type, AccountStatus status, int loginTimes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.type = type;
        this.status = status;
        this.loginTimes = loginTimes;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getType() {
        return type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public int getLoginTimes() {
        return loginTimes;
    }
}
