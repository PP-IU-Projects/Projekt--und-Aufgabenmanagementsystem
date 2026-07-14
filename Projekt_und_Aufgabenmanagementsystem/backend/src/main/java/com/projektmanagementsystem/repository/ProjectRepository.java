package com.projektmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projektmanagementsystem.entity.Project;

/**
 * Repository für den Zugriff auf Projekte.
 *
 * Dieses Repository stellt alle Standardmethoden von
 * JpaRepository zur Verfügung, beispielsweise zum
 * Speichern, Aktualisieren, Löschen und Suchen von
 * Projekten.
 *
 * Zusätzlich enthält es eigene Abfragen für spezielle
 * Anwendungsfälle.
 */
public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    /*
     * Liefert alle Projekte,
     * bei denen ein Benutzer Mitglied ist.
     *
     * Die Suche erfolgt anhand der
     * E-Mail-Adresse des Benutzers.
     */
    @Query("""
        SELECT p
        FROM Project p
        JOIN p.members m
        WHERE m.email = :email
    """)
    List<Project> findProjectsByMemberEmail(
            @Param("email") String email);
}