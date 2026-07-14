package com.projektmanagementsystem.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Service zur Erstellung und Validierung von JWT Tokens.
 *
 * Diese Klasse übernimmt alle Funktionen rund um JSON Web Tokens (JWT).
 *
 * Aufgaben:
 * - JWT Token erzeugen
 * - E-Mail aus einem Token auslesen
 * - Gültigkeit eines Tokens prüfen
 * - Inhalte eines Tokens lesen
 *
 * Der Token enthält:
 * - E-Mail des Benutzers
 * - Ausstellungsdatum
 * - Ablaufdatum
 */
@Service
public class JwtService {

    /*
     * Geheimer Schlüssel zum Signieren der JWT Tokens.
     *
     * Dieser Schlüssel muss ausreichend lang sein,
     * damit der HS256 Algorithmus verwendet werden kann.
     *
     * Hinweis:
     * In Produktivsystemen sollte dieser Wert niemals
     * im Quellcode stehen, sondern z.B. in der
     * application.properties oder als Umgebungsvariable.
     */
    private static final String SECRET_KEY =
            "meinSuperGeheimerJwtSchluesselFuerDasProjektmanagementsystem123456";

    /*
     * Gültigkeitsdauer eines Tokens.
     *
     * 24 Stunden
     */
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24;

    /*
     * Erstellt einen neuen JWT Token.
     *
     * Als Subject wird die E-Mail des Benutzers gespeichert.
     */
    public String generateToken(String email) {

        return Jwts.builder()

                /*
                 * Benutzeridentifikation
                 */
                .subject(email)

                /*
                 * Zeitpunkt der Erstellung
                 */
                .issuedAt(new Date())

                /*
                 * Ablaufzeitpunkt
                 */
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME))

                /*
                 * Token digital signieren
                 */
                .signWith(getSigningKey())

                /*
                 * JWT als String zurückgeben
                 */
                .compact();
    }

    /*
     * Liest die E-Mail aus einem JWT Token.
     */
    public String extractEmail(String token) {

        return extractClaims(token)
                .getSubject();
    }

    /*
     * Prüft, ob ein Token noch gültig ist.
     *
     * Ein Token ist gültig,
     * solange das Ablaufdatum
     * noch nicht erreicht wurde.
     */
    public boolean isTokenValid(String token) {

        return extractClaims(token)
                .getExpiration()
                .after(new Date());
    }

    /*
     * Liest alle Informationen
     * (Claims) eines JWT Tokens.
     */
    private Claims extractClaims(String token) {

        return Jwts.parser()

                /*
                 * Signatur prüfen
                 */
                .verifyWith(getSigningKey())

                .build()

                /*
                 * Token lesen
                 */
                .parseSignedClaims(token)

                /*
                 * Inhalt zurückgeben
                 */
                .getPayload();
    }

    /*
     * Erstellt den kryptographischen Schlüssel
     * aus dem geheimen String.
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes());
    }
}