package com.projektmanagementsystem.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projektmanagementsystem.dto.ProjectDTO;
import com.projektmanagementsystem.entity.Project;
import com.projektmanagementsystem.service.ProjectService;

/**
 * Mapper für Projekte.
 *
 * Diese Klasse wandelt Project-Entities in ProjectDTOs um.
 *
 * Dadurch werden nur die benötigten Daten an das
 * Frontend übertragen. Rekursive JSON-Strukturen
 * sowie unnötige oder sensible Informationen werden
 * vermieden.
 */
@Component
public class ProjectMapper {

    /*
     * Mapper zum Umwandeln der Projektmitglieder
     * in UserDTOs.
     */
    private final UserMapper userMapper;

    /*
     * Service zur Berechnung des Projektfortschritts.
     */
    private final ProjectService projectService;

    /*
     * Konstruktor mit Dependency Injection.
     */
    public ProjectMapper(
            UserMapper userMapper,
            ProjectService projectService) {

        this.userMapper = userMapper;
        this.projectService = projectService;
    }

    /*
     * Wandelt eine Project-Entity
     * in ein ProjectDTO um.
     */
    public ProjectDTO toDTO(Project project) {

        return new ProjectDTO(

                /*
                 * Projekt-ID
                 */
                project.getId(),

                /*
                 * Projektname
                 */
                project.getName(),

                /*
                 * Projektbeschreibung
                 */
                project.getDescription(),

                /*
                 * Archivierungsstatus
                 */
                project.isArchived(),

                /*
                 * Projektfortschritt berechnen.
                 */
                projectService.getProjectProgress(
                        project.getId()),

                /*
                 * Projektmitglieder in UserDTOs umwandeln.
                 */
                project.getMembers()
                        .stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }
}