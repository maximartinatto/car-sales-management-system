package com.martinatto.autos.autos_backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class AdminUserConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            @Value("${magin.admin1.username}") String admin1Username,
            @Value("${magin.admin1.password-hash}") String admin1PasswordHash,
            @Value("${magin.admin2.username}") String admin2Username,
            @Value("${magin.admin2.password-hash}") String admin2PasswordHash
    ) {
        return new InMemoryUserDetailsManager(
                User.withUsername(admin1Username)
                        .password(admin1PasswordHash)
                        .roles("ADMIN")
                        .build(),
                User.withUsername(admin2Username)
                        .password(admin2PasswordHash)
                        .roles("ADMIN")
                        .build()
        );
    }


    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

}
