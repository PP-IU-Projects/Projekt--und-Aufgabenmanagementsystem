package com.projektmanagementsystem.dto;

import java.util.List;

/**
 * DTO für die Übertragung von Projektdaten.
 *
 * Diese Klasse enthält ausschließlich die Informationen,
 * die vom Backend an das Frontend übertragen werden.
 *
 * Durch die Verwendung eines DTOs werden keine kompletten
 * Entity-Objekte übertragen. Dadurch werden unnötige Daten,
 * rekursive JSON-Strukturen und sensible Informationen
 * vermieden.
 */
public class ProjectDTO {

    /*
     * Eindeutige ID des Projekts.
     */
    private Long id;

    /*
     * Name des Projekts.
     */
    private String name;

    /*
     * Beschreibung des Projekts.
     */
    private String description;

    /*
     * Gibt an, ob das Projekt archiviert wurde.
     */
    private boolean archived;

    /*
     * Projektfortschritt in Prozent.
     */
    private double progress;

    /*
     * Mitglieder des Projekts.
     *
     * Es werden UserDTOs verwendet,
     * damit keine sensiblen Benutzerdaten
     * übertragen werden.
     */
    private List<UserDTO> members;

    /*
     * Standardkonstruktor.
     */
    public ProjectDTO() {
    }

    /*
     * Erstellt ein neues ProjectDTO.
     */
    public ProjectDTO(
            Long id,
            String name,
            String description,
            boolean archived,
            double progress,
            List<UserDTO> members) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.archived = archived;
        this.progress = progress;
        this.members = members;
    }

    /*
     * Gibt die Projekt-ID zurück.
     */
    public Long getId() {
        return id;
    }

    /*
     * Setzt die Projekt-ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * Gibt den Projektnamen zurück.
     */
    public String getName() {
        return name;
    }

    /*
     * Setzt den Projektnamen.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Gibt die Projektbeschreibung zurück.
     */
    public String getDescription() {
        return description;
    }

    /*
     * Setzt die Projektbeschreibung.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * Gibt zurück, ob das Projekt archiviert ist.
     */
    public boolean isArchived() {
        return archived;
    }

    /*
     * Setzt den Archivierungsstatus.
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    /*
     * Gibt den Projektfortschritt zurück.
     */
    public double getProgress() {
        return progress;
    }

    /*
     * Setzt den Projektfortschritt.
     */
    public void setProgress(double progress) {
        this.progress = progress;
    }

    /*
     * Gibt alle Projektmitglieder zurück.
     */
    public List<UserDTO> getMembers() {
        return members;
    }

    /*
     * Setzt die Projektmitglieder.
     */
    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}