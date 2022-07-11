package io.sankha.skilltracker.apigateway.configuration;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class SimpleCorsConfiguration {

  /*@Bean
  SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http.cors(Customizer.withDefaults()).csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        //.authenticationManager(authManager)
        //.securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/oauth/token", "/api/v1/user/registerUser").permitAll()
        //.pathMatchers(HttpMethod.GET, "/apiv1/user", "/apiv1/user/**").permitAll()
        .pathMatchers(HttpMethod.POST, "/api/v1/user/createUser").hasAnyRole("ADMIN_PRIVILEGE")
        .pathMatchers(HttpMethod.POST, "/api/v1/user/name").hasAnyRole("ADMIN_PRIVILEGE", "WRITE_PRIVILEGE", "READ_PRIVILEGE")
        .anyExchange().authenticated()
        .and().build();
  }*/

  @Bean
  CorsWebFilter corsWebFilter() {
    var corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Collections.singletonList("*"));
    corsConfig.setMaxAge(8000L);
    corsConfig.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "DELETE", "PUT", "PATCH"));
    corsConfig.addAllowedHeader("*");

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}
