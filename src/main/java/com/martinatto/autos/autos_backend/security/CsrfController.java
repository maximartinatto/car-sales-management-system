
package com.martinatto.autos.autos_backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CsrfController {

    @GetMapping("/api/auth/csrf")
    public Map<String, String> csrf(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(
                CsrfToken.class.getName()
        );

        return Map.of(
                "headerName", token.getHeaderName(),
                "token", token.getToken()
        );
    }
}

