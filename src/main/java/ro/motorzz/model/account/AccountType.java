package ro.motorzz.model.account;

import ro.motorzz.core.exception.InvalidArgumentException;

import java.util.stream.Stream;

public enum AccountType {

    USER, ADMIN;

    public static AccountType valueFromString(String type) {
        return Stream.of(AccountType.values()).filter(g -> g.name().equalsIgnoreCase(type)).findAny().orElseThrow(() -> new InvalidArgumentException("Invalid type value"));
    }

}
