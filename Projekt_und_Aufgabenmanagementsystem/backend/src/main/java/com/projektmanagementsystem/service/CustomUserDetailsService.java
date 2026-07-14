package com.projektmanagementsystem.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.repository.UserRepository;

/**
 * Benutzerverwaltung für Spring Security.
 *
 * Diese Klasse wird von Spring Security verwendet,
 * um einen Benutzer anhand seiner E-Mail-Adresse
 * aus der Datenbank zu laden.
 *
 * Sie liefert die für die Authentifizierung
 * benötigten Informationen wie Benutzername,
 * Passwort und Benutzerrolle.
 *
 * Die Klasse wird unter anderem verwendet bei:
 * - Login
 * - JWT-Authentifizierung
 * - Rollenprüfung mit @PreAuthorize
 */
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    /*
     * Repository zum Zugriff auf die Benutzer.
     */
    private final UserRepository userRepository;

    /*
     * Konstruktor mit Dependency Injection.
     */
    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /*
     * Lädt einen Benutzer anhand seiner
     * E-Mail-Adresse.
     *
     * Diese Methode wird automatisch von
     * Spring Security während der Anmeldung
     * aufgerufen.
     */
    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        /*
         * Benutzer anhand der E-Mail-Adresse suchen.
         *
         * Existiert kein Benutzer, wird eine
         * UsernameNotFoundException ausgelöst.
         */
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Benutzer nicht gefunden"));

        /*
         * Benutzerinformationen an Spring Security
         * zurückgeben.
         *
         * Enthalten sind:
         * - E-Mail-Adresse
         * - Verschlüsseltes Passwort
         * - Benutzerrolle
         */
        return new org.springframework.security.core.userdetails.User(

                /*
                 * Benutzername für Spring Security.
                 */
                user.getEmail(),

                /*
                 * Verschlüsseltes Passwort.
                 */
                user.getPassword(),

                /*
                 * Benutzerrolle.
                 *
                 * Spring Security erwartet den
                 * Präfix "ROLE_".
                 *
                 * Beispiele:
                 * ROLE_ADMIN
                 * ROLE_PROJECT_MANAGER
                 * ROLE_EMPLOYEE
                 */
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" +
                                user.getRole().name()
                        )
                )
        );
    }
}