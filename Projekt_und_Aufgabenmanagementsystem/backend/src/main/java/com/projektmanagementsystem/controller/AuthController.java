package com.projektmanagementsystem.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.projektmanagementsystem.dto.LoginRequestDTO;
import com.projektmanagementsystem.dto.LoginResponseDTO;
import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.repository.UserRepository;
import com.projektmanagementsystem.security.JwtService;

import jakarta.validation.Valid;

/*
 * Controller für die Benutzerauthentifizierung.
 *
 * Diese Klasse stellt den Login-Endpunkt des Systems bereit.
 *
 * Aufgaben:
 * - Anmeldedaten entgegennehmen
 * - Benutzer über Spring Security authentifizieren
 * - JWT Token erzeugen
 * - Token und Benutzerdaten an das Frontend zurückgeben
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    /*
     * Führt die Authentifizierung des Benutzers durch.
     */
    private final AuthenticationManager authenticationManager;

    /*
     * Erstellt JWT Tokens.
     */
    private final JwtService jwtService;

    /*
     * Zugriff auf die Benutzerdatenbank.
     */
    private final UserRepository userRepository;

    /*
     * Konstruktor Injection.
     */
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /*
     * Login-Endpunkt.
     *
     * URL:
     * POST /api/auth/login
     *
     * Ablauf:
     * 1. Login-Daten entgegennehmen
     * 2. Benutzer authentifizieren
     * 3. Benutzer aus der Datenbank laden
     * 4. JWT Token erzeugen
     * 5. Token und Benutzerdaten zurückgeben
     */
    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request) {

        /*
         * Benutzer über Spring Security authentifizieren.
         *
         * Bei falschen Zugangsdaten wird automatisch
         * eine Exception ausgelöst.
         */
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()));

        /*
         * Authentifizierten Benutzer aus der Datenbank laden.
         */
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        /*
         * JWT Token erstellen.
         */
        String token =
                jwtService.generateToken(
                        user.getEmail());

        /*
         * Token und Benutzerdaten an das Frontend senden.
         */
        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole().name());
    }
}