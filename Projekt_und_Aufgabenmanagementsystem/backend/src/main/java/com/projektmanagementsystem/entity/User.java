package com.projektmanagementsystem.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Repräsentiert einen Benutzer des Projektmanagementsystems.
 *
 * Ein Benutzer besitzt persönliche Anmeldedaten, eine Rolle
 * sowie eine Zuordnung zu mehreren Projekten. Die Rolle
 * bestimmt, auf welche Funktionen des Systems der Benutzer
 * Zugriff besitzt.
 */
@Entity
@Table(name = "users")
public class User {

    /*
     * Eindeutige ID des Benutzers.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Benutzername.
     */
    @NotBlank(message = "Benutzername darf nicht leer sein")
    private String username;

    /*
     * E-Mail-Adresse des Benutzers.
     * Wird gleichzeitig für die Anmeldung verwendet.
     */
    @Email(message = "Ungültige E-Mail-Adresse")
    @NotBlank(message = "E-Mail darf nicht leer sein")
    private String email;

    /*
     * Passwort des Benutzers.
     *
     * Das Passwort wird ausschließlich beim Schreiben
     * (Request) verarbeitet und niemals an das Frontend
     * zurückgegeben.
     */
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(
        min = 8,
        message = "Passwort muss mindestens 8 Zeichen lang sein"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /*
     * Rolle des Benutzers.
     * Steuert die Berechtigungen innerhalb des Systems.
     */
    @NotNull(message = "Rolle muss gesetzt werden")
    @Enumerated(EnumType.STRING)
    private Role role;

    /*
     * Projekte, denen der Benutzer zugeordnet ist.
     */
    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<Project> projects;

    /*
     * Standardkonstruktor.
     */
    public User() {
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

    /*
     * Gibt alle zugeordneten Projekte zurück.
     */
    public Set<Project> getProjects() {
        return projects;
    }

    /*
     * Setzt die zugeordneten Projekte.
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}