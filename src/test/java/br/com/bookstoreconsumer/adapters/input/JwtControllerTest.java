package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.configuration.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtControllerTest {

    @InjectMocks
    private JwtController jwtController;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("Should successfully generate JWT token when using manual SecurityContext")
    void testGenerateJwtWithSecurityContext() throws Exception {
        OAuth2AuthenticationToken authenticationToken = Mockito.mock(OAuth2AuthenticationToken.class);

        OAuth2User principal = new DefaultOAuth2User(
                null,
                Map.of("email", "test@example.com"),
                "email"
        );

        when(authenticationToken.getPrincipal()).thenReturn(principal);
        when(jwtUtil.generateJwt(Mockito.any())).thenReturn("access_token");

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<Object> jwtToken = jwtController.generateJwt(authenticationToken);

        assertEquals(200, jwtToken.getStatusCode().value());
    }


    @Test
    @DisplayName("Should return 401 when OAuth2AuthenticationToken is null")
    void testGenerateJwtShouldReturn401WhenTokenIsNull() throws Exception {

        ResponseEntity<Object> jwtToken = jwtController.generateJwt(null);

        assertEquals(401, jwtToken.getStatusCode().value());
    }


}

