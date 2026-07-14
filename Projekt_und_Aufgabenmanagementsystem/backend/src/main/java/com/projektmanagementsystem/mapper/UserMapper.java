package com.projektmanagementsystem.mapper;

import org.springframework.stereotype.Component;

import com.projektmanagementsystem.dto.CreateUserDTO;
import com.projektmanagementsystem.dto.UserDTO;
import com.projektmanagementsystem.entity.User;

/**
 * Mapper für Benutzer.
 *
 * Diese Klasse wandelt Benutzer-Entities
 * in UserDTOs sowie CreateUserDTOs in
 * User-Entities um.
 *
 * Dadurch werden nur die benötigten Daten
 * zwischen Frontend und Backend übertragen.
 */
@Component
public class UserMapper {

    /*
     * Wandelt eine User-Entity
     * in ein UserDTO um.
     */
    public UserDTO toDTO(User user) {

        return new UserDTO(

                /*
                 * Benutzer-ID
                 */
                user.getId(),

                /*
                 * Benutzername
                 */
                user.getUsername(),

                /*
                 * Benutzerrolle
                 */
                user.getRole().name()
        );
    }

    /*
     * Wandelt ein CreateUserDTO
     * in eine User-Entity um.
     *
     * Das Passwort wird hier noch
     * nicht verschlüsselt.
     *
     * Die Verschlüsselung erfolgt
     * anschließend im UserService.
     */
    public User toEntity(CreateUserDTO dto) {

        User user = new User();

        /*
         * Benutzername übernehmen.
         */
        user.setUsername(dto.getUsername());

        /*
         * E-Mail-Adresse übernehmen.
         */
        user.setEmail(dto.getEmail());

        /*
         * Passwort übernehmen.
         *
         * Die Verschlüsselung erfolgt
         * im UserService.
         */
        user.setPassword(dto.getPassword());

        /*
         * Benutzerrolle übernehmen.
         */
        user.setRole(dto.getRole());

        return user;
    }
}