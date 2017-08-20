/**
 *
 */
package ro.motorzz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple swagger groups i.e. same code base multiple swagger
     * resource listings.
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("default")
                .apiInfo(apiInfo()).select()
//                .paths(PathSelectors.regex("/api.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        //TODO fix these
        return new ApiInfoBuilder()
                .title("Motorzz documentation")
                .description("Motorzz description")
                .termsOfServiceUrl("http://motorzz.ro")
                .contact("Lucian Baciu")
                .license("Motorzz License")
                .licenseUrl("http://motorzz.ro/license")
                .version("2.0")
                .build();
    }

}
