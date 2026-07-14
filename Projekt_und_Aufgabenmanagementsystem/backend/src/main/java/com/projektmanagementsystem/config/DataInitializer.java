package com.projektmanagementsystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.projektmanagementsystem.entity.Role;
import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.repository.UserRepository;

/**
 * Initialisiert die Anwendung beim Start.
 *
 * Existiert noch kein Administrator,
 * wird automatisch ein Standardadministrator
 * angelegt.
 *
 * Dadurch kann die Anwendung nach einem
 * Git-Clone oder mit einer neuen Datenbank
 * sofort verwendet werden.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    /*
     * Repository zum Zugriff auf die Benutzer.
     */
    private final UserRepository userRepository;

    /*
     * BCrypt zum Verschlüsseln des Passworts.
     */
    private final PasswordEncoder passwordEncoder;

    /*
     * Konstruktor mit Dependency Injection.
     */
    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Wird automatisch beim Start
     * der Anwendung ausgeführt.
     */
    @Override
    public void run(String... args) {

        /*
         * Prüfen, ob der Standardadministrator
         * bereits existiert.
         */
        if (userRepository.findByEmail("admin@test.de").isPresent()) {
            return;
        }

        /*
         * Neuen Administrator erstellen.
         */
        User admin = new User();

        admin.setUsername("Administrator");
        admin.setEmail("admin@test.de");

        /*
         * Passwort wird direkt verschlüsselt
         * gespeichert.
         */
        admin.setPassword(
                passwordEncoder.encode("admin123"));

        admin.setRole(Role.ADMIN);

        /*
         * Administrator in der Datenbank speichern.
         */
        userRepository.save(admin);

        /*
         * Ausgabe in der Konsole.
         */
        System.out.println("==========================================");
        System.out.println(" Standardadministrator wurde erstellt");
        System.out.println("------------------------------------------");
        System.out.println(" E-Mail   : admin@test.de");
        System.out.println(" Passwort : admin123");
        System.out.println("==========================================");
    }
}