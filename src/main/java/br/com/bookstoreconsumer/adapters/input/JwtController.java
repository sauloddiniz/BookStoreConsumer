package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.configuration.JwtSecurityPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class JwtController {

    private final JwtSecurityPort securityPort;

    public JwtController(JwtSecurityPort securityPort) {
        this.securityPort = securityPort;
    }

    @GetMapping("/generate-token")
    public ResponseEntity<?> generateJwt(OAuth2AuthenticationToken token) {

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        String jwtToken =
                securityPort.generateJwt(token.getPrincipal().toString());

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(Map.of(
                        "Message","Login Successful",
                        "token", jwtToken));
    }
}

