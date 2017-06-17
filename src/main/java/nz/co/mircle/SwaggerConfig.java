package nz.co.mircle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Configuration file for swagger.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Mircle REST API",
                "A list of APIs that are used by the Mircle application",
                "1.0",
                "Terms of service URL here",
                new Contact("Jack Tan", "Contact URL here", "jacktan165@gmail.com"),
                "Mircle License Version 2.0",
                "License URL here",
                new ArrayList<VendorExtension>());
        return apiInfo;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nz.co.mircle"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
