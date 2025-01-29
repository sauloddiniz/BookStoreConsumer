package br.com.bookstoreconsumer.adapters.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtValidationFilter jwtValidationFilter;

    public SecurityConfig(JwtValidationFilter jwtValidationFilter) {
        this.jwtValidationFilter = jwtValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/api-docs.yaml",
                                    "/auth/generate-token"
                            ).permitAll();
                            authorize.anyRequest().authenticated();
                        }
                )
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(
                        oAuth2Configurer ->
                                oAuth2Configurer
                                        .successHandler((request, response, authentication) ->
                                                response.sendRedirect("/auth/generate-token")
                                        )
                );
        return http.build();
    }
}
