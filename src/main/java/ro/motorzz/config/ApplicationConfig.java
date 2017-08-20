package ro.motorzz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    /**
     * This is used for async calls done by @Async annotation
     */
    @Bean(name = "asyncExecutor")
    public TaskExecutor getAsyncTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setThreadNamePrefix("mailSenderExecutor-");
        return taskExecutor;
    }

    @Bean
    public PasswordEncoder getPasswordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

}
