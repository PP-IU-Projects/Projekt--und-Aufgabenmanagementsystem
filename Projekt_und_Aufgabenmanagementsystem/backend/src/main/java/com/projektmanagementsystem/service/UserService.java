package com.projektmanagementsystem.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projektmanagementsystem.entity.User;
import com.projektmanagementsystem.exception.ResourceNotFoundException;
import com.projektmanagementsystem.repository.ProjectRepository;
import com.projektmanagementsystem.repository.TaskRepository;
import com.projektmanagementsystem.repository.UserRepository;
import com.projektmanagementsystem.entity.Project;
import com.projektmanagementsystem.entity.Task;

/**
 * Service zur Verwaltung von Benutzern.
 *
 * Diese Klasse enthält die Geschäftslogik
 * für die Benutzerverwaltung.
 *
 * Zu den Aufgaben gehören:
 * - Benutzer laden
 * - Benutzer speichern
 * - Benutzer löschen
 * - Passwörter mit BCrypt verschlüsseln
 *
 * Der Service bildet die Verbindung zwischen
 * Controller und Repository.
 */
@Service
public class UserService {
	/*
	 * Prüft,
	 * ob bereits Benutzer existieren.
	 */
	public boolean hasUsers() {

	    return userRepository.existsBy();
	}
    /*
     * Repository für Benutzer.
     */
    private final UserRepository userRepository;

    /*
     * BCrypt Passwortverschlüsselung.
     */
    private final PasswordEncoder passwordEncoder;

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    /*
     * Konstruktor mit Dependency Injection.
     */
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ProjectRepository projectRepository,
            TaskRepository taskRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    /*
     * Lädt alle Benutzer aus der Datenbank.
     */
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    /*
     * Sucht einen Benutzer anhand seiner ID.
     *
     * Existiert kein Benutzer,
     * wird eine ResourceNotFoundException ausgelöst.
     */
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Benutzer nicht gefunden"));
    }

    /*
     * Speichert einen Benutzer.
     *
     * Neue Passwörter werden automatisch
     * mit BCrypt verschlüsselt.
     */
    public User saveUser(User user) {

        /*
         * Passwort nur verschlüsseln,
         * wenn eines übergeben wurde.
         */
        if (user.getPassword() != null
                && !user.getPassword().isBlank()) {

            /*
             * Verhindert eine doppelte
             * Verschlüsselung beim Bearbeiten
             * eines Benutzers.
             */
            if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {

                user.setPassword(
                        passwordEncoder.encode(
                                user.getPassword()));
            }
        }

        return userRepository.save(user);
    }

    /*
     * Löscht einen Benutzer.
     *
     * Vor dem Löschen werden:
     * - alle Projektzuordnungen entfernt
     * - alle Aufgaben freigegeben
     * - anschließend der Benutzer gelöscht
     */
    public void deleteUser(Long id) {

        /*
         * Benutzer laden
         */
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Benutzer nicht gefunden"));

        /*
         * Benutzer aus allen Projekten entfernen.
         */
        for (Project project : user.getProjects()) {

            project.getMembers().remove(user);

            projectRepository.save(project);
        }

        /*
         * Alle Aufgaben dieses Benutzers freigeben.
         */
        for (Task task : taskRepository.findByAssignee(user)) {

            task.setAssignee(null);

            taskRepository.save(task);
        }

        /*
         * Benutzer löschen.
         */
        userRepository.delete(user);
    }
}