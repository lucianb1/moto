package ro.motorzz.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import ro.motorzz.test.mock.EdgeServer;

/**
 * Created by Luci on 23-Jun-17.
 */
public class BaseControllerClient {

    @Autowired
    protected EdgeServer edgeServer;

}
