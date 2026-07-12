# Projekt--und-Aufgabenmanagementsystem
Webbasierte Anwendung zur Verwaltung von Projekten, Aufgaben und Mitarbeitenden in einem IT-Dienstleistungsunternehmen.
Die Anwendung wurde als Full-Stack-Webanwendung mit React und Spring Boot entwickelt und bietet eine rollenbasierte Benutzerverwaltung,
sowie eine sichere Authentifizierung über JSON Web Tokens (JWT).


Für das Starten der Anwendung

Backend (Spring Boot):

Start über Eclipse mit Run As → Spring Boot App

Danach im Browser unter http://localhost:5173 erreichbar, in der fertigen Version ist die Seite durch Spring Sec. geschützt und nicht mehr einsehbar.


Frontend 

Eingabeaufforderung starten:

Falls du noch nicht im Frontend-Ordner bist wechsele mit cd auf den Frontend Ordner und bestätige.

Danach npm run dev eingeben und ausführen.

Danach ist das Frontend unter http://localhost:5173/ erreichbar.



Verwendete Technologien

Frontend
React
React Router
Axios
Material UI (MUI)
JavaScript
HTML5
CSS3
Node.js
npm
Vite

Backend
Java
Spring Boot
Spring Security
JSON Web Token (JWT)
Spring Data JPA (Hibernate)
REST API
Maven

Datenbank
PostgreSQL
Architektur
Layered Architecture
REST-Schnittstellen
JSON
DTO (Data Transfer Objects)

Mapper
CRUD-Operationen
Rollenbasierte Zugriffskontrolle (Role-Based Access Control)


Entwicklungswerkzeuge
Eclipse IDE
Visual Studio Code
Git
GitHub

Funktionen
Benutzerverwaltung (CRUD)
Projektverwaltung (CRUD)
Aufgabenverwaltung (CRUD)
Benutzer Projekten zuweisen
Aufgaben Projekten und Benutzern zuweisen
Projektfortschritt mit Fortschrittsanzeige
Login mit JWT-Authentifizierung
Rollenbasierte Autorisierung (Administrator / Benutzer)
Eingabevalidierung
Sichere REST-Kommunikation zwischen Frontend und Backend

Architektur

                   +-------------------------+
                   |        Benutzer         |
                   +-----------+-------------+
                               |
                               | HTTP
                               v
+----------------------------------------------------------+
|                    React Frontend                        |
|----------------------------------------------------------|
| Dashboard | Login | Users | Projects | Tasks | Sidebar   |
| React Router | Axios | Material UI                   |
+-------------------------+-------------------------------+
                          |
                          | REST / JSON
                          v
+----------------------------------------------------------+
|               Spring Boot Backend                        |
|----------------------------------------------------------|
| Controller                                                |
| Service                                                   |
| DTO / Mapper                                              |
| Spring Security + JWT                                     |
| Repository (Spring Data JPA)                              |
+-------------------------+-------------------------------+
                          |
                          | JPA / Hibernate
                          v
+----------------------------------------------------------+
|                    PostgreSQL                            |
|----------------------------------------------------------|
| users | project | tasks | project_members               |
+----------------------------------------------------------+

Klassendiagramm

              +----------------+
              |      User      |
              +----------------+
              | id             |
              | username       |
              | email          |
              | password       |
              | role           |
              +----------------+
                      ▲
                      │ bearbeitet
                      │ 1
                      │
                      │ *
              +----------------+
              |      Task      |
              +----------------+
              | id             |
              | title          |
              | description    |
              | status         |
              +----------------+
                      ▲
                      │ gehört zu
                      │ *
                      │ 1
              +----------------+
              |    Project     |
              +----------------+
              | id             |
              | name           |
              | description    |
              | archived       |
              +----------------+

          User * <-------> * Project
                  Mitglied
                  
            Einfaches Entity-Relationship-Diagramm (ERD)
  +------------+        +------------------+        +------------+
  |   USERS    |        | PROJECT_MEMBERS  |        |  PROJECT   |
  +------------+        +------------------+        +------------+
  | PK id      |<------>| FK user_id       |<------>| PK id      |
  | username   |        | FK project_id    |        | name       |
  | email      |        +------------------+        | description|
  | password   |                                  | archived   |
  | role       |                                  +------------+
  +------------+                                         |
                                                         |
                                                         | 1:n
                                                         |
                                                  +-------------+
                                                  |    TASKS    |
                                                  +-------------+
                                                  | PK id       |
                                                  | title       |
                                                  | description |
                                                  | status      |
                                                  | FK project  |
                                                  | FK assignee |
                                                  +-------------+
                                                         |
                                                         |
                                                         v
                                                   +------------+
                                                   |   USERS    |
                                                   +------------+
