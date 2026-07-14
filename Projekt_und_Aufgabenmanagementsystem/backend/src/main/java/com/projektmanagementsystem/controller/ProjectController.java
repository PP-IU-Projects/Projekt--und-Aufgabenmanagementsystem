package com.projektmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.projektmanagementsystem.dto.ProjectDTO;
import com.projektmanagementsystem.entity.Project;
import com.projektmanagementsystem.mapper.ProjectMapper;
import com.projektmanagementsystem.service.ProjectService;

import jakarta.validation.Valid;

/*
 * Controller zur Verwaltung aller Projekte.
 *
 * Diese Klasse stellt die REST-Endpunkte für
 * die Projektverwaltung bereit.
 *
 * Funktionen:
 * - Projekte anzeigen
 * - Projekte erstellen
 * - Projekte bearbeiten
 * - Projekte löschen
 * - Benutzer Projekten zuweisen
 * - Benutzer aus Projekten entfernen
 * - Projekte archivieren
 * - Projektfortschritt berechnen
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    /*
     * Mapper zum Umwandeln von Project
     * in ProjectDTO.
     */
    private final ProjectMapper projectMapper;

    /*
     * Enthält die Geschäftslogik
     * der Projektverwaltung.
     */
    @Autowired
    private ProjectService projectService;

    /*
     * Konstruktor Injection.
     */
    public ProjectController(
            ProjectService projectService,
            ProjectMapper projectMapper) {

        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    /*
     * Liefert alle Projekte.
     *
     * Administratoren sehen alle Projekte.
     * Projektleiter und Mitarbeiter sehen
     * nur ihre eigenen Projekte.
     *
     * Rückgabe erfolgt als ProjectDTO,
     * damit keine kompletten Entities
     * übertragen werden.
     */
    @GetMapping
    public List<ProjectDTO> getAllProjects() {

        return projectService.getAllProjects()
                .stream()
                .map(projectMapper::toDTO)
                .toList();
    }

    /*
     * Liefert ein einzelnes Projekt
     * anhand seiner ID.
     */
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(
            @PathVariable Long id) {

        return projectMapper.toDTO(
                projectService.getProjectById(id));
    }

    /*
     * Erstellt ein neues Projekt.
     *
     * Nur Administratoren und
     * Projektleiter dürfen Projekte anlegen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PostMapping
    public Project createProject(
            @Valid @RequestBody Project project) {

        return projectService.saveProject(project);
    }

    /*
     * Aktualisiert ein bestehendes Projekt.
     *
     * Die Projekt-ID wird aus der URL übernommen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PutMapping("/{id}")
    public Project updateProject(
            @PathVariable Long id,
            @RequestBody Project project) {

        project.setId(id);

        return projectService.saveProject(project);
    }

    /*
     * Löscht ein Projekt.
     *
     * Nur Administratoren und Projektleiter
     * dürfen Projekte löschen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @DeleteMapping("/{id}")
    public void deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);
    }

    /*
     * Fügt einen Benutzer einem Projekt hinzu.
     *
     * URL:
     * PUT /api/projects/{projectId}/members/{userId}
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PutMapping("/{projectId}/members/{userId}")
    public Project assignUserToProject(
            @PathVariable Long projectId,
            @PathVariable Long userId) {

        return projectService.assignUserToProject(
                projectId,
                userId);
    }

    /*
     * Archiviert ein Projekt.
     *
     * Das Projekt bleibt in der Datenbank
     * erhalten, wird jedoch als archiviert markiert.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PutMapping("/{projectId}/archive")
    public Project archiveProject(
            @PathVariable Long projectId) {

        return projectService.archiveProject(projectId);
    }

    /*
     * Berechnet den Fortschritt eines Projekts.
     *
     * Rückgabe:
     * {
     *   "progress": 75.0
     * }
     */
    @GetMapping("/{projectId}/progress")
    public Map<String, Double> getProjectProgress(
            @PathVariable Long projectId) {

        double progress =
                projectService.getProjectProgress(projectId);

        return Map.of("progress", progress);
    }

    /*
     * Entfernt einen Benutzer aus einem Projekt.
     *
     * URL:
     * DELETE /api/projects/{projectId}/members/{userId}
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @DeleteMapping("/{projectId}/members/{userId}")
    public Project removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId) {

        return projectService.removeMember(
                projectId,
                userId);
    }
}
