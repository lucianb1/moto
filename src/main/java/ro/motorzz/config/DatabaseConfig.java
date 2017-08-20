package ro.motorzz.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Contains database configurations.
 */
@Configuration
@ConfigurationProperties(prefix = "params.datasource")
public class DatabaseConfig extends HikariConfig {

    @Autowired
    private Environment env;


    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return new HikariDataSource(this);
    }

    @Bean
    public JdbcTemplate jdbcTemplateBean(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate createNamedJDBCTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
