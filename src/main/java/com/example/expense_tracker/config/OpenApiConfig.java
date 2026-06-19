package com.example.expense_tracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker API")
                        .description("Production-ready REST API for managing personal expenses. " +
                                     "Supports full CRUD operations with validation and error handling.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Expense Tracker Team")
                                .email("dev@expense-tracker.com")
                                .url("https://github.com/expense-tracker"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:9090")
                                .description("Local Development Server")
                ));
    }
}
