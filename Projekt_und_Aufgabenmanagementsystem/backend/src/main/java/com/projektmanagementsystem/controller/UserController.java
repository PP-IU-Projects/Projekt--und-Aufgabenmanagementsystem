package com.projektmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.projektmanagementsystem.dto.CreateUserDTO;
import com.projektmanagementsystem.dto.UserDTO;
import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.mapper.UserMapper;
import com.projektmanagementsystem.service.UserService;

import jakarta.validation.Valid;

/*
 * Controller zur Verwaltung aller Benutzer.
 *
 * Diese Klasse stellt die REST-Endpunkte für
 * die Benutzerverwaltung bereit.
 *
 * Funktionen:
 * - Benutzer anzeigen
 * - Benutzer erstellen
 * - Benutzer bearbeiten
 * - Benutzer löschen
 *
 * Der erste Benutzer kann ohne Anmeldung
 * erstellt werden.
 *
 * Danach dürfen neue Benutzer ausschließlich
 * von Administratoren angelegt werden.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    /*
     * Enthält die Geschäftslogik
     * der Benutzerverwaltung.
     */
    @Autowired
    private UserService userService;

    /*
     * Mapper zum Umwandeln zwischen
     * User und UserDTO.
     */
    @Autowired
    private UserMapper userMapper;

    /*
     * Liefert alle Benutzer.
     *
     * Rückgabe erfolgt als UserDTO,
     * damit keine Passwörter an das
     * Frontend übertragen werden.
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {

        return userService.getAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    /*
     * Liefert einen Benutzer
     * anhand seiner ID.
     */
    @GetMapping("/{id}")
    public UserDTO getUserById(
            @PathVariable Long id) {

        return userMapper.toDTO(
                userService.getUserById(id));
    }

    /*
     * Erstellt einen neuen Benutzer.
     *
     * Besonderheit:
     *
     * Existiert noch kein Benutzer,
     * darf der erste Administrator
     * ohne Anmeldung erstellt werden.
     *
     * Danach dürfen ausschließlich
     * Administratoren weitere Benutzer
     * anlegen.
     */
    @PostMapping
    public UserDTO createUser(
            @Valid @RequestBody CreateUserDTO dto) {

        /*
         * Existieren bereits Benutzer?
         */
        if (userService.hasUsers()) {

            /*
             * Aktuell angemeldeten Benutzer ermitteln.
             */
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            /*
             * Prüfen, ob der Benutzer
             * Administrator ist.
             */
            boolean isAdmin =
                    authentication != null &&
                    authentication.getAuthorities()
                            .stream()
                            .anyMatch(authority ->
                                    authority.getAuthority()
                                            .equals("ROLE_ADMIN"));

            /*
             * Nur Administratoren dürfen
             * weitere Benutzer anlegen.
             */
            if (!isAdmin) {

                throw new RuntimeException(
                        "Nur Administratoren dürfen Benutzer anlegen.");
            }
        }

        /*
         * DTO in Entity umwandeln.
         */
        User user =
                userMapper.toEntity(dto);

        /*
         * Benutzer speichern.
         */
        User savedUser =
                userService.saveUser(user);

        /*
         * Gespeicherten Benutzer
         * als DTO zurückgeben.
         */
        return userMapper.toDTO(savedUser);
    }

    /*
     * Aktualisiert einen vorhandenen Benutzer.
     *
     * Die Benutzer-ID wird aus der URL übernommen.
     */
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        user.setId(id);

        return userService.saveUser(user);
    }

    /*
     * Löscht einen Benutzer.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);
    }
}
