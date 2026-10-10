
package com.placement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.placement.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authenticationProvider(authenticationProvider)

            .authorizeHttpRequests(auth -> auth

                // Public endpoints
                .requestMatchers(
                    "/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error"
                ).permitAll()

                // Admin-only endpoints
                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                .requestMatchers("/students/**")
                    .hasRole("ADMIN")

                // View job drives: STUDENT, COMPANY, ADMIN
                .requestMatchers(
                    HttpMethod.GET,
                    "/job-drives",
                    "/job-drives/**"
                ).hasAnyRole("STUDENT", "COMPANY", "ADMIN")

                // Create or update job drives: COMPANY, ADMIN
                .requestMatchers(
                    HttpMethod.POST,
                    "/job-drives",
                    "/job-drives/**"
                ).hasAnyRole("COMPANY", "ADMIN")

                // Delete job drives: ADMIN only
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/job-drives",
                    "/job-drives/**"
                ).hasRole("ADMIN")

                // All remaining endpoints require authentication
                .anyRequest().authenticated()
            )

            // Register JWT filter
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}
