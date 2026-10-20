
package com.martinatto.autos.autos_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf
                        .csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse()
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        // Obtener token CSRF
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/auth/csrf"
                        ).permitAll()

                        // Iniciar sesión
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        ).permitAll()

                        // Consultar sesión y cerrar sesión
                        .requestMatchers(
                                "/api/auth/me",
                                "/api/auth/logout"
                        ).hasRole("ADMIN")

                        // Imágenes públicas de vehículos
                        .requestMatchers(
                                HttpMethod.GET,
                                "/uploads/autos/**"
                        ).permitAll()

                        // Catálogo público
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/autos",
                                "/api/autos/**"
                        ).permitAll()

                        // Solicitud pública
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/solicitudes-autos/publica"
                        ).permitAll()

                        // Administración de vehículos
                        .requestMatchers(
                                "/api/autos",
                                "/api/autos/**"
                        ).hasRole("ADMIN")

                        // Administración de clientes
                        .requestMatchers(
                                "/api/clientes",
                                "/api/clientes/**"
                        ).hasRole("ADMIN")

                        // Administración de solicitudes
                        .requestMatchers(
                                "/api/solicitudes-autos",
                                "/api/solicitudes-autos/**"
                        ).hasRole("ADMIN")

                        // Administración de ventas
                        .requestMatchers(
                                "/api/ventas",
                                "/api/ventas/**"
                        ).hasRole("ADMIN")

                        // Administración de pagos
                        .requestMatchers(
                                "/api/pagos",
                                "/api/pagos/**"
                        ).hasRole("ADMIN")

                        // Denegar endpoints no configurados
                        .anyRequest().denyAll()
                );

        return http.build();
    }
}

