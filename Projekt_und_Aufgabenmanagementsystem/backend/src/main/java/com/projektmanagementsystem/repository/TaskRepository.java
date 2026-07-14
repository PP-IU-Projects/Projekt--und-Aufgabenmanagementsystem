package com.projektmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.projektmanagementsystem.entity.User;


import com.projektmanagementsystem.entity.Task;

/**
 * Repository für den Zugriff auf Aufgaben.
 *
 * Dieses Repository stellt alle Standardmethoden von
 * JpaRepository zur Verfügung.
 *
 * Dazu gehören beispielsweise:
 * - Aufgaben speichern
 * - Aufgaben aktualisieren
 * - Aufgaben löschen
 * - Aufgaben anhand ihrer ID suchen
 * - Alle Aufgaben laden
 *
 * Eigene Datenbankabfragen können bei Bedarf
 * später ergänzt werden.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
    /*
     * Liefert alle Aufgaben,
     * die einem bestimmten Benutzer
     * zugewiesen sind.
     */
	List<Task> findByAssignee(User assignee);
}