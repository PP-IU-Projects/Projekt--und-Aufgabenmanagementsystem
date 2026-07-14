package com.projektmanagementsystem.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Repräsentiert ein Projekt im Projektmanagementsystem.
 *
 * Ein Projekt besitzt einen Namen, eine Beschreibung,
 * einen Archivierungsstatus sowie zugeordnete Mitglieder
 * und Aufgaben.
 */
@Entity
@Table(name = "project")
public class Project {

    /*
     * Eindeutige ID des Projekts.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Name des Projekts.
     */
    @NotBlank(message = "Projektname darf nicht leer sein")
    private String name;

    /*
     * Beschreibung des Projekts.
     */
    @NotBlank(message = "Beschreibung darf nicht leer sein")
    private String description;

    /*
     * Kennzeichnet, ob das Projekt archiviert wurde.
     */
    private boolean archived;

    /*
     * Alle Benutzer, die Mitglied des Projekts sind.
     */
    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("project")
	private Set<User> members = new HashSet<>();

	/*
	 * Alle Aufgaben, die zu diesem Projekt gehören.
	 *
	 * cascade = CascadeType.ALL → Änderungen am Projekt werden auf die Aufgaben
	 * übertragen.
	 *
	 * orphanRemoval = true → Werden Aufgaben aus dem Projekt entfernt oder das
	 * Projekt gelöscht, werden die Aufgaben ebenfalls aus der Datenbank gelöscht.
	 */
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("project")
	private List<Task> tasks = new ArrayList<>();

	/*
	 * Gibt die ID des Projekts zurück.
	 */
	public Long getId() {
		return id;
    }

    /*
     * Setzt die ID des Projekts.
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
     * Gibt alle Projektmitglieder zurück.
     */
    public Set<User> getMembers() {
        return members;
    }

    /*
     * Setzt die Mitglieder des Projekts.
     */
    public void setMembers(Set<User> members) {
        this.members = members;
    }

    /*
     * Gibt alle Aufgaben des Projekts zurück.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /*
     * Setzt die Aufgaben des Projekts.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}