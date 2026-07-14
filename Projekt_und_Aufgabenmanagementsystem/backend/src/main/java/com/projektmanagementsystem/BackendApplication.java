package com.projektmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse der Projektmanagement-Anwendung.
 *
 * Diese Klasse dient als Einstiegspunkt der
 * Spring-Boot-Anwendung.
 *
 * Durch die Annotation @SpringBootApplication werden
 * automatisch die Spring-Konfiguration, das Component
 * Scanning sowie die Auto-Konfiguration aktiviert.
 */
@SpringBootApplication
public class BackendApplication {

    /*
     * Startet die Spring-Boot-Anwendung.
     */
    public static void main(String[] args) {

        SpringApplication.run(
                BackendApplication.class,
                args);
    }
}