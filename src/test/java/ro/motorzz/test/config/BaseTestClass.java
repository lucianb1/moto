package ro.motorzz.test.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import ro.motorzz.core.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Luci on 04-Mar-17.
 */
@ContextConfiguration(classes = {TestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
public class BaseTestClass extends AbstractTestNGSpringContextTests {


    private static Random random = new Random();

    public static String randomEmail() {
        return randomString(5) + "@" + randomString(5) + ".com";
    }

    public static String randomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static Double randomLatitude() {
        return RandomUtils.nextDouble(0, 180) - 90D;
    }

    public static Double randomLongitude() {
        return RandomUtils.nextDouble(0, 360) - 180D;
    }

    public static Date randomDate() {
        // Get an Epoch value roughly between 1940 and 2010
        // -946771200000L = January 1, 1940
        // Add up to 70 years to it (using modulus on the next long)
        long ms = -946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

        return new Date(ms);
    }

    public static LocalDate randomLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public <T> T randomElementFromCollection(Collection<T> collection) {
        return collection.stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Empty collection"));
    }

}
