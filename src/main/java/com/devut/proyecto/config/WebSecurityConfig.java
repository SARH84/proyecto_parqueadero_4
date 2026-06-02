package com.devut.proyecto.config;

import static com.devut.proyecto.config.model.Constans.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            		
                // Ruta de autenticación pública
            		
                .requestMatchers(LOGIN_URL).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Rutas públicas sin token
                
                .requestMatchers(HttpMethod.GET, "/api/vehiculos/vencidos").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/vehiculos/por-vencer").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/vehiculos/placa/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/conductores/pueden-operar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/personas/conteo-por-tipo").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/personas").permitAll()

                // Todo lo demás requiere token
                
                .anyRequest().authenticated()
            )
            .addFilterAfter(jwtAuthorizationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
