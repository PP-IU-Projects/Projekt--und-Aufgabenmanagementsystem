package com.projektmanagementsystem.mapper;

import org.springframework.stereotype.Component;

import com.projektmanagementsystem.dto.TaskDTO;
import com.projektmanagementsystem.entity.Task;

/**
 * Mapper für Aufgaben.
 *
 * Diese Klasse wandelt Task-Entities in TaskDTOs um.
 *
 * Dadurch werden nur die benötigten Daten an das
 * Frontend übertragen. Komplette Benutzer- oder
 * Projektobjekte sowie rekursive JSON-Strukturen
 * werden vermieden.
 */
@Component
public class TaskMapper {

    /*
     * Wandelt eine Task-Entity
     * in ein TaskDTO um.
     */
    public TaskDTO toDTO(Task task) {

        return new TaskDTO(

                /*
                 * Aufgaben-ID
                 */
                task.getId(),

                /*
                 * Titel der Aufgabe
                 */
                task.getTitle(),

                /*
                 * Beschreibung der Aufgabe
                 */
                task.getDescription(),

                /*
                 * Aktueller Bearbeitungsstatus
                 */
                task.getStatus(),

                /*
                 * ID des zugehörigen Projekts.
                 *
                 * Falls kein Projekt zugewiesen ist,
                 * wird null zurückgegeben.
                 */
                task.getProject() != null
                        ? task.getProject().getId()
                        : null,

                /*
                 * Name des zugehörigen Projekts.
                 */
                task.getProject() != null
                        ? task.getProject().getName()
                        : null,

                /*
                 * ID des zugewiesenen Benutzers.
                 *
                 * Falls kein Bearbeiter vorhanden ist,
                 * wird null zurückgegeben.
                 */
                task.getAssignee() != null
                        ? task.getAssignee().getId()
                        : null,

                /*
                 * Benutzername des Bearbeiters.
                 */
                task.getAssignee() != null
                        ? task.getAssignee().getUsername()
                        : null
        );
    }
}