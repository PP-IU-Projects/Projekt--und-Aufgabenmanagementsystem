package com.projektmanagementsystem.dto;

import com.projektmanagementsystem.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO zum Erstellen eines neuen Benutzers.
 *
 * Diese Klasse enthält ausschließlich die Daten,
 * die beim Anlegen eines neuen Benutzers vom
 * Frontend an das Backend übertragen werden.
 *
 * Durch die Validierungsannotation werden
 * fehlerhafte Eingaben bereits vor der
 * Verarbeitung erkannt.
 */
public class CreateUserDTO {

    /*
     * Benutzername des neuen Benutzers.
     */
    @NotBlank(message = "Benutzername darf nicht leer sein")
    private String username;

    /*
     * E-Mail-Adresse des Benutzers.
     * Muss eine gültige E-Mail-Adresse sein.
     */
    @Email(message = "Ungültige E-Mail-Adresse")
    @NotBlank(message = "E-Mail darf nicht leer sein")
    private String email;

    /*
     * Passwort des Benutzers.
     * Muss mindestens acht Zeichen lang sein.
     */
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(
            min = 8,
            message = "Passwort muss mindestens 8 Zeichen lang sein"
    )
    private String password;

    /*
     * Rolle des neuen Benutzers.
     */
    @NotNull(message = "Rolle muss ausgewählt werden")
    private Role role;

    /*
     * Standardkonstruktor.
     */
    public CreateUserDTO() {
    }

    /*
     * Gibt den Benutzernamen zurück.
     */
    public String getUsername() {
        return username;
    }

    /*
     * Setzt den Benutzernamen.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Gibt die E-Mail-Adresse zurück.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Setzt die E-Mail-Adresse.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Gibt das Passwort zurück.
     */
    public String getPassword() {
        return password;
    }

    /*
     * Setzt das Passwort.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * Gibt die Benutzerrolle zurück.
     */
    public Role getRole() {
        return role;
    }

    /*
     * Setzt die Benutzerrolle.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}