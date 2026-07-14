package com.projektmanagementsystem.dto;

/**
 * DTO für die Übertragung von Benutzerdaten.
 *
 * Diese Klasse enthält ausschließlich die Informationen,
 * die vom Backend an das Frontend übertragen werden.
 *
 * Sensible Daten wie das Passwort werden bewusst
 * nicht übertragen.
 */
public class UserDTO {

    /*
     * Eindeutige ID des Benutzers.
     */
    private Long id;

    /*
     * Benutzername.
     */
    private String username;

    /*
     * Rolle des Benutzers.
     */
    private String role;

    /*
     * Standardkonstruktor.
     */
    public UserDTO() {
    }

    /*
     * Erstellt ein neues UserDTO.
     */
    public UserDTO(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    /*
     * Gibt die Benutzer-ID zurück.
     */
    public Long getId() {
        return id;
    }

    /*
     * Setzt die Benutzer-ID.
     */
    public void setId(Long id) {
        this.id = id;
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