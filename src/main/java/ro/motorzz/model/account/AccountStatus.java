package ro.motorzz.model.account;

import ro.motorzz.core.exception.InvalidArgumentException;

import java.util.stream.Stream;

public enum AccountStatus {

    PENDING, ACTIVE;

    public static AccountStatus valueFromString(String type) {
        return Stream.of(AccountStatus.values()).filter(g -> g.name().equalsIgnoreCase(type)).findAny().orElseThrow(() -> new InvalidArgumentException("Invalid status value"));
    }


}
