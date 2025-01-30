package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.configuration.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class JwtController {

    private final JwtUtil jwtUtil;

    public JwtController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/generate-token")
    public ResponseEntity<Object> generateJwt(OAuth2AuthenticationToken token) {

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        OAuth2User principal = token.getPrincipal();

        String jwtToken =
                jwtUtil.generateJwt(principal);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(Map.of(
                        "Message","Login Successful",
                        "token", jwtToken));
    }
}

