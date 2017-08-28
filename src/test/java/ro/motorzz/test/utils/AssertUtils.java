package ro.motorzz.test.utils;

import org.springframework.http.HttpStatus;
import org.testng.Assert;
import ro.motorzz.test.edgeserver.EdgeServerResponse;

/**
 * Created by Luci on 23-Jun-17.
 */
public class AssertUtils {

    public static void assertIsSuccessful(EdgeServerResponse response) {
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    public static void assertIsUnauthorized(EdgeServerResponse response) {
        Assert.assertEquals(response.getStatus(), HttpStatus.UNAUTHORIZED.value());
    }


}
