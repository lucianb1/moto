package ro.motorzz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Alexandru on 26.06.2017.
 */
@Configuration
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/swagger/", "classpath:/public/"};


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//        registry.addResourceHandler("/images/users/**").addResourceLocations("file:" + rootFolder + usersRoot + "/");

    }
}
