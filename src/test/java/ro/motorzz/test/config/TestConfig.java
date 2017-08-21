package ro.motorzz.test.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import ro.motorzz.test.mock.EdgeServer;

import javax.servlet.Filter;

/**
 * Created by Luci on 04-Mar-17.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ro.motorzz"})
public class TestConfig {

    @Bean
    public EdgeServer edgeServerBean(WebApplicationContext context, @Qualifier("springSecurityFilterChain") Filter filter) {
        return new EdgeServer(context, filter);
    }


}
