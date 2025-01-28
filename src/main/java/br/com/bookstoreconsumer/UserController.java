package br.com.bookstoreconsumer;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userinfo")
public class UserController {

    @GetMapping
    public String userInfo(@AuthenticationPrincipal OAuth2User principal) {
        return "Usuário autenticado: " + principal.getAttributes();
    }
}

