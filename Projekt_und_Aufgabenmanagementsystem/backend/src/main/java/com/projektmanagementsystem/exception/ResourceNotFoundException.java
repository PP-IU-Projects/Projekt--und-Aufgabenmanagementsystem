package com.projektmanagementsystem.exception;

/*
 * Exception für nicht gefundene Ressourcen.
 *
 * Diese Exception wird verwendet, wenn ein
 * Benutzer, Projekt oder eine Aufgabe anhand
 * einer ID oder eines anderen Suchkriteriums
 * nicht in der Datenbank gefunden werden kann.
 *
 * Die Exception wird anschließend vom
 * GlobalExceptionHandler abgefangen und als
 * HTTP-Status 404 (Not Found) an das Frontend
 * zurückgegeben.
 */
public class ResourceNotFoundException extends RuntimeException {

    /*
     * Seriennummer der Exception-Klasse.
     *
     * Wird für die Serialisierung verwendet.
     */
    private static final long serialVersionUID = 1L;

    /*
     * Konstruktor mit individueller Fehlermeldung.
     *
     * Beispiel:
     * "Projekt nicht gefunden"
     * "Benutzer nicht gefunden"
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}