package br.com.bookstoreconsumer.adapters.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "secretKey");
    }

    @Test
    @DisplayName("Should return true for a valid JWT")
    void shouldValidateJwtSuccessfully() {

        OAuth2User principal = new DefaultOAuth2User(
                null,
                Map.of("email", "test@example.com",
                        "sub", "1234567890",
                        "name", "batman"
                ),
                "email"
        );
        String jwtToken = jwtUtil.generateJwt(principal);
        jwtToken = "Bearer " + jwtToken;

        boolean isValid = jwtUtil.validJwt(jwtToken);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return false for a valid JWT")
    void shouldValidateJwtError() {

        String jwtToken = "fake_token";

        boolean isValid = jwtUtil.validJwt(jwtToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should extract email from a valid JWT")
    void shouldExtractEmailFromJwt() {

        OAuth2User principal = new DefaultOAuth2User(
                null,
                Map.of("email", "test@example.com",
                        "sub", "1234567890",
                        "name", "batman"
                ),
                "email"
        );

        String jwtToken = jwtUtil.generateJwt(principal);
        jwtToken = "Bearer " + jwtToken;

        String extractedEmail = jwtUtil.getEmail(jwtToken);

        assertNotNull(extractedEmail);
        assertEquals("test@example.com", extractedEmail);
    }

}
