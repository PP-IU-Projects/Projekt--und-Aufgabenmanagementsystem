package com.projektmanagementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projektmanagementsystem.entity.Project;
import com.projektmanagementsystem.entity.Task;
import com.projektmanagementsystem.entity.TaskStatus;
import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.exception.ResourceNotFoundException;
import com.projektmanagementsystem.repository.ProjectRepository;
import com.projektmanagementsystem.repository.TaskRepository;
import com.projektmanagementsystem.repository.UserRepository;

/**
 * Service zur Verwaltung von Aufgaben.
 *
 * Diese Klasse enthält die Geschäftslogik
 * für die Aufgabenverwaltung.
 *
 * Zu den Aufgaben gehören:
 * - Aufgaben laden
 * - Aufgaben speichern
 * - Aufgaben löschen
 * - Aufgaben Projekten zuweisen
 * - Aufgaben Benutzern zuweisen
 * - Aufgaben als erledigt markieren
 */
@Service
public class TaskService {

    /*
     * Repository für Aufgaben.
     */
    private final TaskRepository taskRepository;

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
    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /*
     * Lädt alle Aufgaben aus der Datenbank.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /*
     * Speichert eine neue Aufgabe oder
     * aktualisiert eine bestehende Aufgabe.
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /*
     * Sucht eine Aufgabe anhand ihrer ID.
     *
     * Existiert keine Aufgabe,
     * wird eine Exception ausgelöst.
     */
    public Task getTaskById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aufgabe nicht gefunden"));
    }

    /*
     * Löscht eine Aufgabe anhand ihrer ID.
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /*
     * Ordnet eine Aufgabe
     * einem Projekt zu.
     */
    public Task assignTaskToProject(
            Long taskId,
            Long projectId) {

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aufgabe nicht gefunden"));

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Projekt nicht gefunden"));

        task.setProject(project);

        return taskRepository.save(task);
    }

    /*
     * Weist eine Aufgabe
     * einem Benutzer zu.
     */
    public Task assignTaskToUser(
            Long taskId,
            Long userId) {

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aufgabe nicht gefunden"));

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Benutzer nicht gefunden"));

        task.setAssignee(user);

        return taskRepository.save(task);
    }

    /*
     * Markiert eine Aufgabe
     * als erledigt.
     *
     * Der Status wird auf DONE gesetzt.
     */
    public Task completeTask(Long taskId) {

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aufgabe nicht gefunden"));

        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }
}