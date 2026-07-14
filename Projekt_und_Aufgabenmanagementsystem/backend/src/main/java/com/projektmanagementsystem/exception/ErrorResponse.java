package com.projektmanagementsystem.exception;

import java.time.LocalDateTime;

/*
 * Standardisierte Fehlermeldung des Backends.
 *
 * Diese Klasse wird vom GlobalExceptionHandler verwendet,
 * um Fehler in einem einheitlichen JSON-Format an das
 * Frontend zurückzugeben.
 *
 * Beispiel:
 *
 * {
 *   "timestamp": "2025-07-05T15:30:10",
 *   "status": 404,
 *   "message": "Projekt nicht gefunden"
 * }
 */
public class ErrorResponse {

    /*
     * Zeitpunkt, an dem der Fehler aufgetreten ist.
     */
    private LocalDateTime timestamp;

    /*
     * HTTP-Statuscode.
     *
     * Beispiele:
     * 400 = Bad Request
     * 401 = Unauthorized
     * 403 = Forbidden
     * 404 = Not Found
     * 500 = Internal Server Error
     */
    private int status;

    /*
     * Beschreibung des Fehlers.
     */
    private String message;

    /*
     * Leerer Konstruktor für Jackson.
     */
    public ErrorResponse() {
    }

    /*
     * Konstruktor zum Erstellen einer vollständigen
     * Fehlerantwort.
     */
    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String message) {

        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    /*
     * Getter
     */

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    /*
     * Setter
     */

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}