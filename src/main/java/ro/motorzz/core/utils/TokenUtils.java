package ro.motorzz.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by Luci on 22-Jun-17.
 */
public class TokenUtils {

    private static final int TOKEN_LENGTH = 50;
    private static final int PASSWORD_LENGTH = 6;

    public static String generateToken() {
        return RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
    }

    public static String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
    }

}
