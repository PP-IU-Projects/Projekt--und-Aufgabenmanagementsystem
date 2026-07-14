package com.projektmanagementsystem.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * Zentrale Sicherheitskonfiguration des Projektmanagementsystems.
 *
 * Funktionen:
 * - JWT Authentifizierung
 * - Passwortverschlüsselung mit BCrypt
 * - Rollenbasierte Autorisierung
 * - CORS Unterstützung für React
 * - Zustandslose Sessions
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /*
     * JWT Filter wird vor der eigentlichen
     * Spring Security Authentifizierung ausgeführt.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
     * Konstruktor Injection.
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    /*
     * Passwortverschlüsselung mit BCrypt.
     *
     * BCrypt verwendet automatisch Salt
     * und gilt als aktueller Standard.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * AuthenticationManager für den Login.
     *
     * Wird im AuthController verwendet.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    /*
     * CORS Konfiguration für das React Frontend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        /*
         * React Entwicklungsserver erlauben.
         */
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173"));

        /*
         * Erlaubte HTTP Methoden.
         */
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        /*
         * Alle Header erlauben.
         */
        configuration.setAllowedHeaders(
                List.of("*"));

        /*
         * Authorization Header zulassen.
         */
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }

    /*
     * Hauptkonfiguration von Spring Security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

            /*
             * CORS aktivieren.
             */
            .cors(cors -> {})

            /*
             * CSRF deaktivieren.
             *
             * Für REST APIs mit JWT normalerweise
             * nicht notwendig.
             */
            .csrf(csrf -> csrf.disable())

            /*
             * JWT arbeitet ohne Server Session.
             */
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            /*
             * Zugriffsregeln definieren.
             */
            .authorizeHttpRequests(auth -> auth

                    /*
                     * Login-Endpunkte sind öffentlich.
                     */
                    .requestMatchers(
                            "/api/auth/**")
                    .permitAll()

                    /*
                     * Registrierung eines Benutzers erlauben.
                     *
                     * Ob tatsächlich ein Benutzer erstellt
                     * werden darf, entscheidet der
                     * UserController:
                     *
                     * - erster Benutzer → erlaubt
                     * - danach → nur Administratoren
                     */
                    .requestMatchers(
                            HttpMethod.POST,
                            "/api/users")
                    .permitAll()

                    /*
                     * Preflight Requests für CORS erlauben.
                     */
                    .requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**")
                    .permitAll()

                    /*
                     * Alle übrigen Endpunkte benötigen
                     * einen gültigen JWT Token.
                     */
                    .anyRequest()
                    .authenticated())

            /*
             * JWT Filter vor dem Standardfilter
             * von Spring Security ausführen.
             */
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}