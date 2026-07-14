package com.projektmanagementsystem.dto;

/**
 * DTO für die Antwort nach einem erfolgreichen Login.
 *
 * Diese Klasse enthält die Informationen, die nach
 * einer erfolgreichen Authentifizierung an das
 * Frontend zurückgegeben werden.
 *
 * Das Frontend erhält den JWT-Token sowie den
 * Benutzernamen und die Rolle des angemeldeten
 * Benutzers.
 */
public class LoginResponseDTO {

    /*
     * JWT-Token für die Authentifizierung
     * bei weiteren API-Anfragen.
     */
    private String token;

    /*
     * Benutzername des angemeldeten Benutzers.
     */
    private String username;

    /*
     * Rolle des angemeldeten Benutzers.
     */
    private String role;

    /*
     * Standardkonstruktor.
     */
    public LoginResponseDTO() {
    }

    /*
     * Erstellt eine neue Login-Antwort.
     */
    public LoginResponseDTO(
            String token,
            String username,
            String role) {

        this.token = token;
        this.username = username;
        this.role = role;
    }

    /*
     * Gibt den JWT-Token zurück.
     */
    public String getToken() {
        return token;
    }

    /*
     * Setzt den JWT-Token.
     */
    public void setToken(String token) {
        this.token = token;
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
     * Gibt die Benutzerrolle zurück.
     */
    public String getRole() {
        return role;
    }

    /*
     * Setzt die Benutzerrolle.
     */
    public void setRole(String role) {
        this.role = role;
    }
}