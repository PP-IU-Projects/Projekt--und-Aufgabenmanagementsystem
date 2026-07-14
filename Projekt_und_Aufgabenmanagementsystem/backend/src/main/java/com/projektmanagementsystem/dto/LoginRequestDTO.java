package com.projektmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO für die Login-Anfrage.
 *
 * Diese Klasse enthält die Anmeldedaten, die vom
 * Frontend an das Backend übertragen werden.
 *
 * Für die Anmeldung werden ausschließlich die
 * E-Mail-Adresse und das Passwort benötigt.
 */
public class LoginRequestDTO {

    /*
     * E-Mail-Adresse des Benutzers.
     */
    @Email(message = "Ungültige E-Mail-Adresse")
    @NotBlank(message = "E-Mail darf nicht leer sein")
    private String email;

    /*
     * Passwort des Benutzers.
     */
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;

    /*
     * Standardkonstruktor.
     */
    public LoginRequestDTO() {
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
}