package br.com.bookstoreconsumer.adapters.configuration;

import br.com.bookstoreconsumer.adapters.filters.JwtValidationFilter;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtValidationFilter jwtValidationFilter;
    private final SecurityPort securityPort;

    public SecurityConfig(JwtValidationFilter jwtValidationFilter, SecurityPort securityPort) {
        this.jwtValidationFilter = jwtValidationFilter;
        this.securityPort = securityPort;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS
                        ))
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/api-docs.yaml"
                            ).permitAll();
                            authorize.anyRequest().authenticated();
                        }
                )
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(
                        oAuth2Configurer ->
                                oAuth2Configurer
                                        .successHandler((request, response, authentication) -> {
                                            OidcUser principal = (OidcUser) authentication.getPrincipal();
                                            String email = principal.getEmail();
                                            String name = principal.getGivenName();
                                            Cookie jwtCookie = securityPort.generateJwtCookie(email, name);
                                            response.addCookie(jwtCookie);
                                            response.sendRedirect("/swagger-ui/index.html");
                                        })
                );
        return http.build();
    }
}
