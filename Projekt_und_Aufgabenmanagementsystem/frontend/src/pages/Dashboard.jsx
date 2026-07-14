import { useEffect, useState } from "react";
import api from "../services/api";

import {
    Typography,
    Card,
    CardContent,
    Grid,
    LinearProgress,
    Box
} from "@mui/material";

/*
 * Dashboard
 *
 * Zeigt eine Übersicht über:
 * - Anzahl der Benutzer
 * - Anzahl der Projekte
 * - Anzahl der Aufgaben
 * - Projektfortschritt
 */
function Dashboard() {

    /*
     * Zustände für Benutzer,
     * Projekte und Aufgaben.
     */
    const [users, setUsers] = useState([]);
    const [projects, setProjects] = useState([]);
    const [tasks, setTasks] = useState([]);

    /*
     * Lädt alle Daten
     * beim ersten Öffnen
     * des Dashboards.
     */
    useEffect(() => {
        loadData();
    }, []);

    /*
     * Lädt Benutzer,
     * Projekte und Aufgaben
     * vom Backend.
     */
    const loadData = () => {
        api.get("/users").then(res => setUsers(res.data));
        api.get("/projects").then(res => setProjects(res.data));
        api.get("/tasks").then(res => setTasks(res.data));
    };

    /*
     * Berechnet den Fortschritt
     * eines Projekts anhand
     * erledigter Aufgaben.
     */
    const calculateProgress = (projectId) => {

        const projectTasks = tasks.filter(
            task => task.project && task.project.id === projectId
        );

        if (projectTasks.length === 0) {
            return 0;
        }

        const completedTasks = projectTasks.filter(
            task => task.status === "DONE"
        );

        return Math.round(
            (completedTasks.length / projectTasks.length) * 100
        );
    };

    return (
        <div>

            <Typography variant="h4" gutterBottom>
                Projektmanagement Dashboard
            </Typography>

            <Grid container spacing={3} sx={{ mb: 4 }}>

                <Grid item xs={12} md={4}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">
                                Benutzer
                            </Typography>
                            <Typography variant="h3">
                                {users.length}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} md={4}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">
                                Projekte
                            </Typography>
                            <Typography variant="h3">
                                {projects.length}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} md={4}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">
                                Aufgaben
                            </Typography>
                            <Typography variant="h3">
                                {tasks.length}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>

            </Grid>

            <Typography variant="h5" gutterBottom>
                Projektfortschritt
            </Typography>

            {projects.map(project => (

                <Card key={project.id} sx={{ mb: 2 }}>
                    <CardContent>

                        <Typography variant="h6">
                            {project.name}
                        </Typography>

                        <Box sx={{ mt: 2 }}>
                            <LinearProgress
                                variant="determinate"
                                value={calculateProgress(project.id)}
                            />
                        </Box>

                        <Typography sx={{ mt: 1 }}>
                            {calculateProgress(project.id)} %
                        </Typography>

                    </CardContent>
                </Card>

            ))}

        </div>
    );
}

export default Dashboard;