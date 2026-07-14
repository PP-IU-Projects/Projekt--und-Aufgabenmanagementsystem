import { Routes, Route } from "react-router-dom";
import { Box, Toolbar } from "@mui/material";

/*
 * Seiten des Systems
 */
import Dashboard from "./pages/Dashboard";
import Users from "./pages/Users";
import Projects from "./pages/Projects";
import Tasks from "./pages/Tasks";
import Login from "./pages/Login";

/*
 * Layout Komponenten
 */
import Sidebar from "./components/Sidebar";
import Navbar from "./components/Navbar";

/*
 * Prüft, ob ein Benutzer eingeloggt ist.
 *
 * Falls kein JWT Token vorhanden ist,
 * wird automatisch zur Login-Seite
 * weitergeleitet.
 */
import ProtectedRoute from "./components/ProtectedRoute";

function App() {

    return (

        /*
         * Hauptlayout der Anwendung.
         *
         * display:flex sorgt dafür,
         * dass Sidebar und Inhalt
         * nebeneinander dargestellt werden.
         */
        <Box sx={{ display: "flex" }}>

            {/* obere Navigationsleiste */}
            <Navbar />

            {/* linke Seitenleiste */}
            <Sidebar />

            {/* Hauptinhalt */}
            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    p: 3
                }}
            >
                {/*
                 * Abstand zur Navbar.
                 *
                 * Ohne Toolbar würde der Inhalt
                 * unter der Navbar beginnen.
                 */}
                <Toolbar />

                {/* Routing der Anwendung */}
                <Routes>

                    {/*
                     * Login darf ohne Anmeldung
                     * geöffnet werden.
                     */}
                    <Route
                        path="/login"
                        element={<Login />}
                    />

                    {/*
                     * Dashboard ist geschützt.
                     */}
                    <Route
                        path="/"
                        element={
                            <ProtectedRoute>
                                <Dashboard />
                            </ProtectedRoute>
                        }
                    />

                    {/*
                     * Benutzerverwaltung
                     * nur nach erfolgreichem Login.
                     */}
                    <Route
                        path="/users"
                        element={
                            <ProtectedRoute>
                                <Users />
                            </ProtectedRoute>
                        }
                    />

                    {/*
                     * Projektverwaltung
                     * nur nach erfolgreichem Login.
                     */}
                    <Route
                        path="/projects"
                        element={
                            <ProtectedRoute>
                                <Projects />
                            </ProtectedRoute>
                        }
                    />

                    {/*
                     * Aufgabenverwaltung
                     * nur nach erfolgreichem Login.
                     */}
                    <Route
                        path="/tasks"
                        element={
                            <ProtectedRoute>
                                <Tasks />
                            </ProtectedRoute>
                        }
                    />

                </Routes>

            </Box>

        </Box>
    );
}

export default App;