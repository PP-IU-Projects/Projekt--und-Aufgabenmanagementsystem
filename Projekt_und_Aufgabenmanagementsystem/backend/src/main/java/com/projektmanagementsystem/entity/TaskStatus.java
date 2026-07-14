package com.projektmanagementsystem.entity;

/**
 * Definiert die möglichen Bearbeitungsstatus
 * einer Aufgabe im Projektmanagementsystem.
 *
 * Der Status wird verwendet, um den aktuellen
 * Fortschritt einer Aufgabe abzubilden und den
 * Projektfortschritt zu berechnen.
 */
public enum TaskStatus {

    /*
     * Aufgabe wurde erstellt,
     * aber noch nicht begonnen.
     */
    OPEN,

    /*
     * Aufgabe wird derzeit bearbeitet.
     */
    IN_PROGRESS,

    /*
     * Aufgabe wurde erfolgreich abgeschlossen.
     */
    DONE
}