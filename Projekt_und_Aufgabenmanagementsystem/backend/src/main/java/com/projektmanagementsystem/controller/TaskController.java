package com.projektmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.projektmanagementsystem.dto.TaskDTO;
import com.projektmanagementsystem.entity.Task;
import com.projektmanagementsystem.mapper.TaskMapper;
import com.projektmanagementsystem.service.TaskService;

import jakarta.validation.Valid;

/*
 * Controller zur Verwaltung aller Aufgaben.
 *
 * Diese Klasse stellt die REST-Endpunkte für
 * die Aufgabenverwaltung bereit.
 *
 * Funktionen:
 * - Aufgaben anzeigen
 * - Aufgaben erstellen
 * - Aufgaben bearbeiten
 * - Aufgaben löschen
 * - Aufgaben Projekten zuweisen
 * - Aufgaben Benutzern zuweisen
 * - Aufgaben als erledigt markieren
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    /*
     * Mapper zum Umwandeln von Task
     * in TaskDTO.
     */
    private final TaskMapper taskMapper;

    /*
     * Enthält die Geschäftslogik
     * der Aufgabenverwaltung.
     */
    @Autowired
    private TaskService taskService;

    /*
     * Konstruktor Injection.
     */
    public TaskController(
            TaskService taskService,
            TaskMapper taskMapper) {

        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /*
     * Liefert alle Aufgaben.
     *
     * Rückgabe erfolgt als TaskDTO,
     * damit keine kompletten Entities
     * an das Frontend übertragen werden.
     */
    @GetMapping
    public List<TaskDTO> getAllTasks() {

        return taskService.getAllTasks()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    /*
     * Liefert eine einzelne Aufgabe
     * anhand ihrer ID.
     */
    @GetMapping("/{id}")
    public TaskDTO getTaskById(
            @PathVariable Long id) {

        return taskMapper.toDTO(
                taskService.getTaskById(id));
    }

    /*
     * Erstellt eine neue Aufgabe.
     *
     * Alle angemeldeten Benutzer dürfen
     * neue Aufgaben erstellen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER','EMPLOYEE')")
    @PostMapping
    public Task createTask(
            @Valid @RequestBody Task task) {

        return taskService.saveTask(task);
    }

    /*
     * Aktualisiert eine bestehende Aufgabe.
     *
     * Die Aufgaben-ID wird aus der URL übernommen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER','EMPLOYEE')")
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task) {

        task.setId(id);

        return taskService.saveTask(task);
    }

    /*
     * Löscht eine Aufgabe.
     *
     * Nur Administratoren und Projektleiter
     * dürfen Aufgaben löschen.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @DeleteMapping("/{id}")
    public void deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);
    }

    /*
     * Ordnet eine Aufgabe einem Projekt zu.
     *
     * URL:
     * PUT /api/tasks/{taskId}/project/{projectId}
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PutMapping("/{taskId}/project/{projectId}")
    public Task assignTaskToProject(
            @PathVariable Long taskId,
            @PathVariable Long projectId) {

        return taskService.assignTaskToProject(
                taskId,
                projectId);
    }

    /*
     * Weist eine Aufgabe einem Benutzer zu.
     *
     * URL:
     * PUT /api/tasks/{taskId}/assignee/{userId}
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    @PutMapping("/{taskId}/assignee/{userId}")
    public Task assignTaskToUser(
            @PathVariable Long taskId,
            @PathVariable Long userId) {

        return taskService.assignTaskToUser(
                taskId,
                userId);
    }

    /*
     * Markiert eine Aufgabe als erledigt.
     *
     * Der Status der Aufgabe wird auf
     * DONE gesetzt.
     */
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER','EMPLOYEE')")
    @PutMapping("/{taskId}/complete")
    public Task completeTask(
            @PathVariable Long taskId) {

        return taskService.completeTask(taskId);
    }
}