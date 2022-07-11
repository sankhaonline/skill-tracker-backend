package io.sankha.skilltracker.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class ApiGatewayApplication {

  public static void main(String... args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  public SecurityWebFilterChain corsWebfilterSpringSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf().disable();
    return http.build();
  }
}
