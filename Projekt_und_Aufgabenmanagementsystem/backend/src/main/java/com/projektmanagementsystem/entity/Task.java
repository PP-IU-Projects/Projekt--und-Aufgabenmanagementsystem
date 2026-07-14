package com.projektmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Repräsentiert eine Aufgabe innerhalb eines Projekts.
 *
 * Eine Aufgabe besitzt einen Titel, eine Beschreibung,
 * einen Bearbeitungsstatus sowie eine Zuordnung zu einem
 * Projekt und optional zu einem Benutzer.
 */
@Entity
@Table(name = "tasks")
public class Task {

    /*
     * Eindeutige ID der Aufgabe.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Titel der Aufgabe.
     */
    @NotBlank(message = "Titel darf nicht leer sein")
    private String title;

    /*
     * Beschreibung der Aufgabe.
     */
    @NotBlank(message = "Beschreibung darf nicht leer sein")
    private String description;

    /*
     * Aktueller Bearbeitungsstatus der Aufgabe.
     */
    @NotNull(message = "Status muss gesetzt werden")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /*
     * Projekt, zu dem die Aufgabe gehört.
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"tasks", "members"})
    private Project project;

    /*
     * Benutzer, dem die Aufgabe zugewiesen wurde.
     */
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @JsonIgnoreProperties({"project"})
    private User assignee;

    /*
     * Standardkonstruktor.
     */
    public Task() {
    }

    /*
     * Gibt die ID der Aufgabe zurück.
     */
    public Long getId() {
        return id;
    }

    /*
     * Setzt die ID der Aufgabe.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * Gibt den Titel der Aufgabe zurück.
     */
    public String getTitle() {
        return title;
    }

    /*
     * Setzt den Titel der Aufgabe.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * Gibt die Beschreibung der Aufgabe zurück.
     */
    public String getDescription() {
        return description;
    }

    /*
     * Setzt die Beschreibung der Aufgabe.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * Gibt den aktuellen Status der Aufgabe zurück.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /*
     * Setzt den Status der Aufgabe.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /*
     * Gibt das zugehörige Projekt zurück.
     */
    public Project getProject() {
        return project;
    }

    /*
     * Setzt das zugehörige Projekt.
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /*
     * Gibt den zugewiesenen Benutzer zurück.
     */
    public User getAssignee() {
        return assignee;
    }

    /*
     * Setzt den zugewiesenen Benutzer.
     */
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
}