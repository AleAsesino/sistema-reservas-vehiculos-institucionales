package com.reservas.vehiculos.institucionales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactivar solo para desarrollo, revisar para producción
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/vehiculos/**").hasAnyRole("ADMIN", "INSPECTOR")
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}
