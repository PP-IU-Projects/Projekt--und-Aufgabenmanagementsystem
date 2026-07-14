package com.projektmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projektmanagementsystem.entity.User;

/**
 * Repository für den Zugriff auf Benutzer.
 *
 * Dieses Repository stellt alle Standardmethoden von
 * JpaRepository zur Verfügung.
 *
 * Dazu gehören beispielsweise:
 * - Benutzer speichern
 * - Benutzer aktualisieren
 * - Benutzer löschen
 * - Benutzer anhand ihrer ID suchen
 * - Alle Benutzer laden
 *
 * Zusätzlich enthält dieses Repository
 * eine eigene Suchmethode für die Anmeldung
 * über Spring Security und JWT.
 */
public interface UserRepository
        extends JpaRepository<User, Long> {

    /*
     * Sucht einen Benutzer anhand
     * seiner E-Mail-Adresse.
     *
     * Die Methode wird beim Login verwendet,
     * um den Benutzer zu authentifizieren.
     *
     * Rückgabe:
     * Optional<User>, da möglicherweise kein
     * Benutzer mit der angegebenen E-Mail
     * existiert.
     */
    Optional<User> findByEmail(String email);
    /*
     * Prüft, ob bereits Benutzer vorhanden sind.
     */
    boolean existsBy();
}