package com.rentoki.wildcatsmplacebackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:Wildcats Marketplace API}")
    private String applicationName;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title(applicationName)
                .version("1.0.0")
                .description("""
                    ## Wildcats Marketplace API
                    
                    A comprehensive university marketplace platform where students can buy and sell resources.
                    
                    ### Features:
                    - Student registration and authentication
                    - Resource marketplace with categories
                    - Student verification system
                    - Reporting and moderation system
                    - Admin management
                    
                    ### Authentication
                    Most endpoints require authentication. Register a student account first, then use the login endpoint to get access.
                    
                    ### Base URL
                    All endpoints are relative to: `/api`
                    """)
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}