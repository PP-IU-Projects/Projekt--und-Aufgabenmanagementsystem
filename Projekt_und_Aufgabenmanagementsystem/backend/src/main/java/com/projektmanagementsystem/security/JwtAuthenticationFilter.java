package com.projektmanagementsystem.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT Authentication Filter.
 *
 * Diese Klasse wird bei jeder eingehenden HTTP-Anfrage
 * automatisch von Spring Security ausgeführt.
 *
 * Aufgaben:
 * - Authorization Header auslesen
 * - JWT Token extrahieren
 * - Benutzer anhand der E-Mail laden
 * - Token validieren
 * - Benutzer bei Spring Security authentifizieren
 *
 * Dadurch erkennt Spring Security bei jeder Anfrage,
 * welcher Benutzer aktuell angemeldet ist.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /*
     * Service zum Lesen und Prüfen
     * von JWT Tokens.
     */
    private final JwtService jwtService;

    /*
     * Service zum Laden von Benutzern
     * aus der Datenbank.
     */
    private final UserDetailsService userDetailsService;

    /*
     * Konstruktor mit Dependency Injection.
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /*
     * Wird bei jeder HTTP-Anfrage
     * automatisch ausgeführt.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        /*
         * Login-Endpunkte werden nicht geprüft,
         * da hier noch kein JWT vorhanden ist.
         */
        String path = request.getServletPath();

        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * Authorization Header lesen.
         *
         * Beispiel:
         *
         * Authorization: Bearer eyJhbGc...
         */
        final String authHeader =
                request.getHeader("Authorization");

        /*
         * Ist kein JWT vorhanden,
         * wird die Anfrage ohne Authentifizierung
         * weitergeleitet.
         */
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        /*
         * "Bearer " vom Token entfernen.
         */
        String jwtToken =
                authHeader.substring(7);

        /*
         * E-Mail aus dem JWT lesen.
         */
        String email =
                jwtService.extractEmail(jwtToken);

        /*
         * Nur authentifizieren,
         * wenn noch kein Benutzer
         * angemeldet wurde.
         */
        if (email != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            /*
             * Benutzer aus der Datenbank laden.
             */
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);

            /*
             * JWT prüfen.
             */
            if (jwtService.isTokenValid(jwtToken)) {

                /*
                 * Authentifizierung erzeugen.
                 */
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                /*
                 * Informationen der aktuellen Anfrage speichern.
                 */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                /*
                 * Benutzer im Security Context speichern.
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        /*
         * Anfrage an den nächsten Filter
         * weitergeben.
         */
        filterChain.doFilter(request, response);
    }
}