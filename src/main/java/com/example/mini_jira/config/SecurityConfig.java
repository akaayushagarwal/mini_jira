package com.example.mini_jira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.mini_jira.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()

                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()

                .requestMatchers("/error").permitAll()

                .requestMatchers(HttpMethod.POST, "/projects").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/projects/**").authenticated()

                .requestMatchers(HttpMethod.POST, "/tickets").hasAnyAuthority("QA", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/tickets/*/assign/**").hasAnyAuthority("DEV", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/tickets/*/status").hasAnyAuthority("QA", "DEV", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/tickets/**").authenticated()

                .requestMatchers("/users/**").hasAuthority("ADMIN")

                .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
