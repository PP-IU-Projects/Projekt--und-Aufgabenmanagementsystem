package com.projektmanagementsystem.dto;

import com.projektmanagementsystem.entity.TaskStatus;

/**
 * DTO für die Übertragung von Aufgabendaten.
 *
 * Diese Klasse enthält ausschließlich die Informationen,
 * die vom Backend an das Frontend übertragen werden.
 *
 * Dadurch werden keine kompletten Benutzer- oder
 * Projektobjekte übertragen und rekursive
 * JSON-Strukturen vermieden.
 */
public class TaskDTO {

    /*
     * Eindeutige ID der Aufgabe.
     */
    private Long id;

    /*
     * Titel der Aufgabe.
     */
    private String title;

    /*
     * Beschreibung der Aufgabe.
     */
    private String description;

    /*
     * Aktueller Bearbeitungsstatus der Aufgabe.
     */
    private TaskStatus status;

    /*
     * ID und Name des zugehörigen Projekts.
     */
    private Long projectId;
    private String projectName;

    /*
     * ID und Name des zugewiesenen Benutzers.
     */
    private Long assigneeId;
    private String assigneeName;

    /*
     * Standardkonstruktor.
     */
    public TaskDTO() {
    }

    /*
     * Erstellt ein neues TaskDTO.
     */
    public TaskDTO(
            Long id,
            String title,
            String description,
            TaskStatus status,
            Long projectId,
            String projectName,
            Long assigneeId,
            String assigneeName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.projectName = projectName;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
    }

    /*
     * Gibt die Aufgaben-ID zurück.
     */
    public Long getId() {
        return id;
    }

    /*
     * Setzt die Aufgaben-ID.
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
     * Gibt den Status der Aufgabe zurück.
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
     * Gibt die Projekt-ID zurück.
     */
    public Long getProjectId() {
        return projectId;
    }

    /*
     * Setzt die Projekt-ID.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /*
     * Gibt den Projektnamen zurück.
     */
    public String getProjectName() {
        return projectName;
    }

    /*
     * Setzt den Projektnamen.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /*
     * Gibt die Benutzer-ID des Bearbeiters zurück.
     */
    public Long getAssigneeId() {
        return assigneeId;
    }

    /*
     * Setzt die Benutzer-ID des Bearbeiters.
     */
    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    /*
     * Gibt den Benutzernamen des Bearbeiters zurück.
     */
    public String getAssigneeName() {
        return assigneeName;
    }

    /*
     * Setzt den Benutzernamen des Bearbeiters.
     */
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}