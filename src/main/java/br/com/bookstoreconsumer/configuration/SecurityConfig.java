package br.com.bookstoreconsumer.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Date;


@Configuration
public class SecurityConfig {

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
                .oauth2Login(
                        oAuth2Configurer ->
                                oAuth2Configurer
                                        .successHandler((request, response, authentication) -> {
                                            OidcUser principal = (OidcUser) authentication.getPrincipal();
                                            String email = principal.getEmail();
                                            String name = principal.getGivenName();
                                            String secretKey = "secreta-chave-de-seguranca";
                                            String jwtToken = JWT.create()
                                                    .withSubject(email)
                                                    .withClaim("name", name)
                                                    .withClaim("email", email)
                                                    .withIssuedAt(new Date())
                                                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                                                    .sign(Algorithm.HMAC256(secretKey));
                                            response.addHeader("Authorization", "Bearer " + jwtToken);
                                            response.addCookie(new Cookie("jwtToken", jwtToken));
                                            response.sendRedirect("/swagger-ui/index.html");
                                        })
                );
        return http.build();
    }
}
