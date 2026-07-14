import { useEffect, useState } from "react";
import api from "../services/api";
import LinearProgress from "@mui/material/LinearProgress";
import handleApiError from "../utils/apiErrorHandler"
import Alert from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";

import {
    Container,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Chip,
    Button,
    TextField,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    MenuItem,
    Box
} from "@mui/material";

function Projects() {

    /*
     * Alle Projekte aus dem Backend
     */
    const [projects, setProjects] = useState([]);

    /*
     * Alle Benutzer aus dem Backend
     */
    const [users, setUsers] = useState([]);
    /*
 * Fehlermeldung
 */
const [errorMessage, setErrorMessage] = useState("");
const [openError, setOpenError] = useState(false);
    /*
     * Eingabefelder für neue Projekte
     */
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

 
    
    /*
     * Speichert die Benutzer-Auswahl
     * Beispiel:
     * {
     *    1: 4,
     *    2: 1
     * }
     *
     * Projekt 1 -> Benutzer 4
     * Projekt 2 -> Benutzer 1
     */
    const [selectedUsers, setSelectedUsers] = useState({});

    /*
     * Bearbeitungsdialog
     */
    const [open, setOpen] = useState(false);
    const [editProject, setEditProject] = useState(null);

    /*
     * Beim Laden der Seite Projekte und Benutzer laden
     */
    useEffect(() => {
        loadProjects();
        loadUsers();
    }, []);

   /*
     * Fortschrittsanzeige
     */
    const [progress, setProgress] = useState({});

    /*
 * Rolle des aktuell angemeldeten Benutzers
 */
const role = localStorage.getItem("role");


  /*
 * Fortschritt eines Projekts laden
 */
const loadProjectProgress = (projectId) => {

    api.get(`/projects/${projectId}/progress`)
        .then((response) => {

            setProgress(prev => ({
                ...prev,
                [projectId]: response.data.progress
            }));

        })
       .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};
    /*
     * Projekte laden
     */
   const loadProjects = () => {
    api.get("/projects")
        .then((response) => {

            setProjects(response.data);

            response.data.forEach(project => {
                loadProjectProgress(project.id);
            });

        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};

    /*
     * Benutzer laden
     */
    const loadUsers = () => {
        api.get("/users")
            .then((response) => {
                setUsers(response.data);
            })
            .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
    };

    /*
     * Neues Projekt erstellen
     */
    const createProject = () => {
        api.post("/projects", {
            name,
            description,
            archived: false
        })
        .then(() => {
            loadProjects();

            setName("");
            setDescription("");
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
    };

    /*
     * Projekt löschen
     */
  	 const deleteProject = (id) => {
         console.log("Projekt löschen:", id);

        api.delete("/projects/" + id)
        .then(() => {
            loadProjects();
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};
/*
 * Projekt archivieren
 */
const archiveProject = (projectId) => {

    api.put(`/projects/${projectId}/archive`)
        .then(() => {
            loadProjects();
        })
        .catch(error => {
            console.error(error);
        });
};
    /*
     * Benutzer einem Projekt hinzufügen
     *
     * Backend:
     * PUT /api/projects/{projectId}/members/{userId}
     */
    const assignUser = (projectId) => {

        const userId = selectedUsers[projectId];

        if (!userId) {
            alert("Bitte zuerst einen Benutzer auswählen.");
            return;
        }

        api.put(
            "/projects/" +
            projectId +
            "/members/" +
            userId
        )
        .then(() => {
            loadProjects();
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
    };

    /*
     * Benutzer aus einem Projekt entfernen
     *
     * Backend:
     * DELETE /api/projects/{projectId}/members/{userId}
     */
    const removeUser = (projectId, userId) => {

        api.delete(
            "/projects/" +
            projectId +
            "/members/" +
            userId
        )
        .then(() => {
            loadProjects();
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
       
    };

    /*
     * Bearbeitungsdialog öffnen
     */
    const openEditDialog = (project) => {
        setEditProject({ ...project });
        setOpen(true);
    };

    /*
     * Projekt speichern
     */
    const saveProject = () => {

        api.put(
            "/projects/" + editProject.id,
            editProject
        )
        .then(() => {
            setOpen(false);
            loadProjects();
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
    };

    return (
        <>

            <Typography variant="h4" gutterBottom>
                Projekte
            </Typography>

	    {(role === "ADMIN" || role === "PROJECT_MANAGER") && (
            <Paper sx={{ p: 3, mb: 3 }}>

                <Typography variant="h6" gutterBottom>
                    Projekt erstellen
                </Typography>

                <TextField
                    label="Projektname"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    fullWidth
                    margin="normal"
                />

                <TextField
                    label="Beschreibung"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    fullWidth
                    margin="normal"
                />

                <Button
                    variant="contained"
                    sx={{ mt: 2 }}
                    onClick={createProject}
                >
                    Projekt erstellen
                </Button>

            </Paper>
   
            )}
	    
            <TableContainer component={Paper}>
                <Table>

                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Beschreibung</TableCell>
 			    <TableCell>Fortschritt</TableCell>
                            <TableCell>Mitglieder</TableCell>
                            <TableCell>Archiviert</TableCell>
                            <TableCell>Mitglied hinzufügen</TableCell>
                            <TableCell>Aktionen</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>

                        {projects
    .filter(project => !project.archived)
    .map((project) => (

                            <TableRow
    key={project.id}
    sx={{
        opacity: project.archived ? 0.5 : 1
    }}
>

                                <TableCell>
                                    {project.id}
                                </TableCell>

                                <TableCell>
                                    {project.name}
                                </TableCell>

                                <TableCell>
                                    {project.description}
                                </TableCell>
				
				<TableCell>

    <Box sx={{ width: 150 }}>

        <LinearProgress
            variant="determinate"
            value={progress[project.id] || 0}
        />

        <Typography variant="body2">
            {Math.round(progress[project.id] || 0)} %
        </Typography>

    </Box>

</TableCell>

                                <TableCell>

                                    {project.members &&
                                    project.members.length > 0 ? (

                                        project.members.map((member) => (

                                            <Box
                                                key={member.id}
                                                sx={{
                                                    display: "flex",
                                                    alignItems: "center",
                                                    gap: 1,
                                                    mb: 1
                                                }}
                                            >

                                                <Chip
                                                    label={
                                                        member.username
                                                    }
                                                    color="primary"
                                                />

                                                <Button
                                                    size="small"
                                                    color="error"
                                                    variant="outlined"
			                        disabled={project.archived}
                                                    onClick={() =>
                                                        removeUser(
                                                            project.id,
                                                            member.id
                                                        )
                                                    }
                                                >
                                                    Entfernen
                                                </Button>

                                            </Box>

                                        ))

                                    ) : (

                                        "Keine Mitglieder"

                                    )}

                                </TableCell>

                                <TableCell>

                                    {project.archived ? (
                                        <Chip
                                            label="Ja"
                                            color="error"
                                        />
                                    ) : (
                                        <Chip
                                            label="Nein"
                                            color="success"
                                        />
                                    )}

                                </TableCell>

                                <TableCell>
				  {(role === "ADMIN" || role === "PROJECT_MANAGER") && (

<>
                                    <TextField
                                        select
                                        size="small"
                                        disabled={project.archived}
                                        sx={{
                                            width: 180,
                                            mr: 1
                                        }}
                                        value={
                                            selectedUsers[
                                                project.id
                                            ] || ""
                                        }
                                        onChange={(e) =>
                                            setSelectedUsers({
                                                ...selectedUsers,
                                                [project.id]:
                                                    e.target.value
                                            })
                                        }
                                    >

                                        {users.map((user) => (

                                            <MenuItem
                                                key={user.id}
                                                value={user.id}
                                            >
                                                {user.username}
                                            </MenuItem>

                                        ))}

                                    </TextField>

                                    <Button
                                        variant="contained"
                                        size="small"
                                        disabled={project.archived}
                                        onClick={() =>
                                            assignUser(
                                                project.id
                                            )
                                        }
                                    >
                                        Hinzufügen
                                    </Button>
				</>

)}
                                </TableCell>

                                <TableCell>

{(role === "ADMIN" || role === "PROJECT_MANAGER") && (

                                    <Button
                                        variant="contained"
                                        color="warning"
                                        sx={{ mr: 1 }}
					disabled={project.archived}
                                        onClick={() =>
                                            openEditDialog(
                                                project
                                            )
                                        }
                                    >
                                        Bearbeiten
                                    </Button>
)}
{(role === "ADMIN" || role === "PROJECT_MANAGER") && (

<Button
    variant="contained"
    color="warning"
    sx={{ mr: 1 }}
    onClick={() =>
        archiveProject(project.id)
    }
>
    Archivieren
</Button>

)}                                  

{(role === "ADMIN" || role === "PROJECT_MANAGER") && (

<Button
    variant="contained"
    color="error"
    onClick={() =>
        deleteProject(project.id)
    }
>
    Löschen
</Button>

)}
                                </TableCell>

                            </TableRow>

                        ))}

                    </TableBody>

                </Table>
            </TableContainer>
	<Typography
    variant="h5"
    sx={{ mt: 5, mb: 2 }}
>
    Archivierte Projekte
</Typography>

<TableContainer component={Paper}>
    <Table>

        <TableHead>
            <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Beschreibung</TableCell>
                <TableCell>Fortschritt</TableCell>
            </TableRow>
        </TableHead>

        <TableBody>

            {projects
                .filter(project => project.archived)
                .map(project => (

                    <TableRow key={project.id}>

                        <TableCell>
                            {project.id}
                        </TableCell>

                        <TableCell>
                            {project.name}
                        </TableCell>

                        <TableCell>
                            {project.description}
                        </TableCell>

                        <TableCell>

                            <Box sx={{ width: 150 }}>

                                <LinearProgress
                                    variant="determinate"
                                    value={
                                        progress[project.id] || 0
                                    }
                                />

                                <Typography variant="body2">
                                    {
                                        Math.round(
                                            progress[project.id] || 0
                                        )
                                    } %
                                </Typography>

                            </Box>

                        </TableCell>

                    </TableRow>

                ))}

        </TableBody>

    </Table>
</TableContainer>
            <Dialog
                open={open}
                onClose={() => setOpen(false)}
            >

                <DialogTitle>
                    Projekt bearbeiten
                </DialogTitle>

                <DialogContent>

                    <TextField
                        label="Projektname"
                        fullWidth
                        margin="normal"
                        value={editProject?.name || ""}
                        onChange={(e) =>
                            setEditProject({
                                ...editProject,
                                name: e.target.value
                            })
                        }
                    />

                    <TextField
                        label="Beschreibung"
                        fullWidth
                        margin="normal"
                        value={
                            editProject?.description || ""
                        }
                        onChange={(e) =>
                            setEditProject({
                                ...editProject,
                                description:
                                    e.target.value
                            })
                        }
                    />

                </DialogContent>

                <DialogActions>

                    <Button
                        onClick={() =>
                            setOpen(false)
                        }
                    >
                        Abbrechen
                    </Button>

                    <Button
                        variant="contained"
                        onClick={saveProject}
                    >
                        Speichern
                    </Button>

                </DialogActions>

            </Dialog>

<Snackbar
    open={openError}
    autoHideDuration={4000}
    onClose={() => setOpenError(false)}
>
    <Alert
        onClose={() => setOpenError(false)}
        severity="error"
        variant="filled"
    >
        {errorMessage}
    </Alert>
</Snackbar>
        </>
    );
}

export default Projects;