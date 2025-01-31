package br.com.bookstoreconsumer.adapters.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomJwtFilter jwtValidationFilter;

    public SecurityConfig(CustomJwtFilter jwtValidationFilter) {
        this.jwtValidationFilter = jwtValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(
                                    "/bookstore-consumer-api/swagger-ui/**",
                                    "/bookstore-consumer-api/v3/api-docs/**",
                                    "/bookstore-consumer-api/api-docs.yaml",
                                    "/bookstore-consumer-api/auth/generate-token"
                            ).permitAll();
                            authorize.anyRequest().authenticated();
                        }
                )
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(
                        oAuth2Configurer ->
                                oAuth2Configurer
                                        .successHandler((request, response, authentication) ->
                                                response.sendRedirect("/bookstore-consumer-api/auth/generate-token")
                                        )
                );
        return http.build();
    }
}
