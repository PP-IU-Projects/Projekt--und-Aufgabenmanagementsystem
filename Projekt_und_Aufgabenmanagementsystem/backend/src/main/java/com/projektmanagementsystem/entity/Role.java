package com.projektmanagementsystem.entity;

/**
 * Definiert die verfügbaren Benutzerrollen
 * im Projektmanagementsystem.
 *
 * Die Rollen werden für die Authentifizierung
 * und Autorisierung verwendet und steuern,
 * auf welche Funktionen ein Benutzer Zugriff hat.
 */
public enum Role {

    /*
     * Administrator mit allen Berechtigungen.
     */
    ADMIN,

    /*
     * Projektleiter mit erweiterten Rechten
     * zur Verwaltung von Projekten und Aufgaben.
     */
    PROJECT_MANAGER,

    /*
     * Mitarbeiter mit eingeschränkten Rechten.
     */
    EMPLOYEE
}