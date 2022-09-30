package com.qnocks.linkshortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Link shortener")
                .version("1.0")
                .description("Application allows to reduce long links and track statistics for your business and " +
                        "projects by monitoring the number of hits from your URL with the click counter")
                .contact(new Contact().name("qnocks").email("roma.ost2012@mail.com")));
    }
}

