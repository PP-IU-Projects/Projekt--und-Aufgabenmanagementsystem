package com.projektmanagementsystem.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Globaler Exception Handler.
 *
 * Diese Klasse fängt alle zentralen Fehler des Backends ab
 * und gibt sie in einem einheitlichen JSON-Format an das
 * Frontend zurück.
 *
 * Dadurch müssen Fehler nicht in jedem Controller einzeln
 * behandelt werden.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Behandlung von ResourceNotFoundException.
     *
     * Diese Exception wird ausgelöst,
     * wenn ein Benutzer, Projekt oder eine Aufgabe
     * nicht gefunden werden konnte.
     *
     * HTTP Status:
     * 404 Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    /*
     * Behandlung von Validierungsfehlern.
     *
     * Diese Methode wird automatisch aufgerufen,
     * wenn Bean Validation fehlschlägt.
     *
     * Beispiele:
     * - leere Eingabefelder
     * - ungültige E-Mail-Adresse
     * - Passwort zu kurz
     *
     * HTTP Status:
     * 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        /*
         * Erste Validierungsfehlermeldung auslesen.
         */
        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message);

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    /*
     * Behandlung aller übrigen Fehler.
     *
     * Diese Methode dient als Fallback,
     * falls keine speziellere Exception
     * gefunden wurde.
     *
     * HTTP Status:
     * 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}