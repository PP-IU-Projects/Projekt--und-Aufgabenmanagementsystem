package com.projektmanagementsystem.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projektmanagementsystem.entity.Project;
import com.projektmanagementsystem.entity.Role;
import com.projektmanagementsystem.entity.TaskStatus;
import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.exception.ResourceNotFoundException;
import com.projektmanagementsystem.repository.ProjectRepository;
import com.projektmanagementsystem.repository.UserRepository;

/**
 * Service zur Verwaltung von Projekten.
 *
 * Diese Klasse enthält die Geschäftslogik für die
 * Projektverwaltung.
 *
 * Zu den Aufgaben gehören unter anderem:
 * - Projekte laden
 * - Projekte speichern
 * - Projekte löschen
 * - Benutzer Projekten zuweisen
 * - Benutzer aus Projekten entfernen
 * - Projekte archivieren
 * - Projektfortschritt berechnen
 *
 * Zusätzlich werden die Berechtigungen der Benutzer
 * berücksichtigt, sodass jeder Benutzer nur die
 * Projekte sehen kann, auf die er Zugriff besitzt.
 */
@Service
public class ProjectService {

    /*
     * Repository für Projekte.
     */
    private final ProjectRepository projectRepository;

    /*
     * Repository für Benutzer.
     */
    private final UserRepository userRepository;

    /*
     * Konstruktor mit Dependency Injection.
     */
    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /*
     * Liefert alle Projekte,
     * die der aktuell angemeldete Benutzer
     * sehen darf.
     *
     * Administratoren sehen alle Projekte.
     * Projektleiter und Mitarbeiter sehen
     * nur Projekte, bei denen sie Mitglied sind.
     */
    public List<Project> getAllProjects() {

        /*
         * Aktuell angemeldeten Benutzer ermitteln.
         */
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        /*
         * Benutzer aus der Datenbank laden.
         */
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Benutzer nicht gefunden"));

        /*
         * Administratoren dürfen alle
         * Projekte sehen.
         */
        if (user.getRole() == Role.ADMIN) {
            return projectRepository.findAll();
        }

        /*
         * Projektleiter und Mitarbeiter
         * sehen ausschließlich ihre Projekte.
         */
        return projectRepository.findProjectsByMemberEmail(email);
    }

    /*
     * Speichert ein neues Projekt oder
     * aktualisiert ein vorhandenes Projekt.
     */
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    /*
     * Sucht ein Projekt anhand seiner ID.
     *
     * Existiert kein Projekt,
     * wird eine Exception ausgelöst.
     */
    public Project getProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Projekt nicht gefunden"));
    }

    /*
     * Löscht ein Projekt anhand seiner ID.
     */
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    /*
     * Fügt einen Benutzer
     * zu einem Projekt hinzu.
     */
    public Project assignUserToProject(
            Long projectId,
            Long userId) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Projekt nicht gefunden"));

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Benutzer nicht gefunden"));

        project.getMembers().add(user);

        return projectRepository.save(project);
    }

    /*
     * Entfernt einen Benutzer
     * aus einem Projekt.
     */
    public Project removeMember(
            Long projectId,
            Long userId) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Projekt nicht gefunden"));

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Benutzer nicht gefunden"));

        project.getMembers().remove(user);

        return projectRepository.save(project);
    }

    /*
     * Archiviert ein Projekt.
     *
     * Das Projekt bleibt erhalten,
     * wird jedoch als archiviert markiert.
     */
    public Project archiveProject(Long projectId) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Projekt nicht gefunden"));

        project.setArchived(true);

        return projectRepository.save(project);
    }

    /*
     * Berechnet den Fortschritt eines Projekts.
     *
     * Grundlage ist das Verhältnis zwischen
     * erledigten Aufgaben und der Gesamtzahl
     * aller Aufgaben.
     *
     * Beispiel:
     * 10 Aufgaben
     * 4 erledigt
     *
     * Ergebnis:
     * 40 %
     */
    public double getProjectProgress(Long projectId) {

        /*
         * Projekt laden.
         */
        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Projekt nicht gefunden"));

        /*
         * Anzahl aller Aufgaben.
         */
        int totalTasks =
                project.getTasks().size();

        /*
         * Sind keine Aufgaben vorhanden,
         * beträgt der Fortschritt 0 %.
         */
        if (totalTasks == 0) {
            return 0;
        }

        /*
         * Anzahl erledigter Aufgaben zählen.
         */
        long completedTasks =
                project.getTasks()
                        .stream()
                        .filter(task ->
                                task.getStatus()
                                        == TaskStatus.DONE)
                        .count();

        /*
         * Fortschritt in Prozent berechnen.
         */
        return ((double) completedTasks
                / totalTasks) * 100;
    }
}