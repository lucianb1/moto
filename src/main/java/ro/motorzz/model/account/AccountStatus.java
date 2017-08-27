package ro.motorzz.model.account;


import ro.motorzz.core.exception.InvalidArgumentException;
import java.util.stream.Stream;

/**
 * Created by Luci on 22-Jun-17.
 */
public enum AccountStatus {

    ACTIVE, PENDING;

    public static AccountStatus valueFromString(String type) {
        return Stream.of(AccountStatus.values()).filter(g -> g.name().equalsIgnoreCase(type)).findAny().orElseThrow(() -> new InvalidArgumentException("Invalid geneder value"));
    }
}
