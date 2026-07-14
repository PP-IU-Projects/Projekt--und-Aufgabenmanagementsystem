import { useEffect, useState } from "react";
import api from "../services/api";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import handleApiError from "../utils/apiErrorHandler";

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
Button,
Chip,
TextField,
Dialog,
DialogTitle,
DialogContent,
DialogActions,
MenuItem
} from "@mui/material";

function Tasks() {

const [tasks, setTasks] = useState([]);
/*
 * Alle Projekte laden
 */
const [projects, setProjects] = useState([]);
/*
 * Fehlermeldung
 */
const [errorMessage, setErrorMessage] = useState("");
const [openError, setOpenError] = useState(false);
/*
 * Ausgewähltes Projekt
 */
const [selectedProjectId, setSelectedProjectId] = useState("");

/*
 * Ausgewählter Benutzer
 */
const [selectedUserId, setSelectedUserId] = useState("");

const [title, setTitle] = useState("");
const [description, setDescription] = useState("");
const [status, setStatus] = useState("OPEN");

const [open, setOpen] = useState(false);
const [editTask, setEditTask] = useState(null);

/*
 * Beim Laden der Seite
 * Aufgaben und Projekte laden
 */
useEffect(() => {
    loadTasks();
    loadProjects();
}, []);

const loadTasks = () => {
    api.get("/tasks")
        .then(response => setTasks(response.data))
        .catch(error => console.error(error));
};

/*
 * Projekte laden
 */
const loadProjects = () => {
    api.get("/projects")
        .then(response => {
            setProjects(response.data);
        })
        .catch(error => {
            console.error(error);
        });
};

/*
 * Das aktuell ausgewählte Projekt
 */
const selectedProject = projects.find(
    project => project.id === Number(selectedProjectId)
);

/*
 * Neue Aufgabe erstellen
 */
const createTask = () => {

    api.post("/tasks", {
        title,
        description,
        status,

        /*
         * Projekt setzen
         */
        project: selectedProjectId
            ? { id: selectedProjectId }
            : null,

        /*
         * Benutzer setzen
         */
        assignee: selectedUserId
            ? { id: selectedUserId }
            : null
    })
    .then(() => {

        loadTasks();

        /*
         * Formular zurücksetzen
         */
        setTitle("");
        setDescription("");
        setStatus("OPEN");
        setSelectedProjectId("");
        setSelectedUserId("");
    })
    .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};

const completeTask = (taskId) => {
    api.put("/tasks/" + taskId + "/complete")
    .then(() => {
        loadTasks();
    })
    .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};
const deleteTask = (id) => {
    console.log("Task löschen:", id);

    api.delete("/tasks/" + id)
        .then(() => {
            loadTasks();
        })
        .catch((error) => {
    handleApiError(
        error,
        setErrorMessage,
        setOpenError
    );
});
};

const openEditDialog = (task) => {
    setEditTask({ ...task });
    setOpen(true);
};

const saveTask = () => {
    api.put(`/tasks/${editTask.id}`, editTask)
        .then(() => {
            setOpen(false);
            loadTasks();
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
            Aufgaben
        </Typography>

        <Paper sx={{ p: 2, mb: 3 }}>

            <Typography variant="h6" gutterBottom>
                Aufgabe erstellen
            </Typography>

            <TextField
                label="Titel"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
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

            <TextField
                select
                label="Status"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
                fullWidth
                margin="normal"
            >
                <MenuItem value="OPEN">OPEN</MenuItem>
                <MenuItem value="IN_PROGRESS">IN_PROGRESS</MenuItem>
                <MenuItem value="DONE">DONE</MenuItem>
            </TextField>
		
	    <TextField
            select
   	    label="Projekt"
  	    value={selectedProjectId}
  	    onChange={(e) => {

  		      /*
  		       * Projekt speichern
   		      */
    		    setSelectedProjectId(e.target.value);

    			    /*
    			     * Benutzer zurücksetzen
     			    */
     			   setSelectedUserId("");
			    }}
  			  fullWidth
 			   margin="normal"
				>
  			  {projects.map(project => (

       			 <MenuItem
        		    key={project.id}
         		   value={project.id}
      			  >
        		    {project.name}
       			 </MenuItem>

  			  ))}
		</TextField>
<TextField
    select
    label="Bearbeiter"
    value={selectedUserId}
    onChange={(e) =>
        setSelectedUserId(e.target.value)
    }
    fullWidth
    margin="normal"

    /*
     * Erst aktivieren wenn
     * ein Projekt ausgewählt wurde
     */
    disabled={!selectedProject}
>

 <MenuItem value="">
        Kein Benutzer ausgewählt
    </MenuItem>
    {
        selectedProject?.members?.map(user => (

            <MenuItem
                key={user.id}
                value={user.id}
            >
                {user.username}
            </MenuItem>

        ))
    }

</TextField>

            <Button
                variant="contained"
                onClick={createTask}
                sx={{ mt: 2 }}
            >
                Aufgabe erstellen
            </Button>

        </Paper>

        <TableContainer component={Paper}>
            <Table>

                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Titel</TableCell>
                        <TableCell>Beschreibung</TableCell>
                        <TableCell>Status</TableCell>
                        <TableCell>Projekt</TableCell>
                        <TableCell>Bearbeiter</TableCell>
                        <TableCell>Aktionen</TableCell>
                    </TableRow>
                </TableHead>

                <TableBody>
                    {tasks.map(task => (
                        <TableRow key={task.id}>

                            <TableCell>{task.id}</TableCell>
                            <TableCell>{task.title}</TableCell>
                            <TableCell>{task.description}</TableCell>

                            <TableCell>
                                <Chip
                                    label={task.status}
                                    color={
                                        task.status === "DONE"
                                            ? "success"
                                            : task.status === "IN_PROGRESS"
                                            ? "warning"
                                            : "default"
                                    }
                                />
                            </TableCell>

                            <TableCell>
                                {task.projectName || "Kein Projekt"}
                            </TableCell>

                            <TableCell>
                                {task.assigneeName  || "Nicht zugewiesen"} 
                            </TableCell>

                            <TableCell>

                                {task.status !== "DONE" && (
                                    <Button
                                        variant="contained"
                                        color="success"
                                        sx={{ mr: 1 }}
                                        onClick={() => 	completeTask(task.id)}
                                    >
                                        Erledigt
                                    </Button>
                                )}

                                <Button
                                    variant="contained"
                                    sx={{ mr: 1 }}
                                    onClick={() => openEditDialog(task)}
                                >
                                    Bearbeiten
                                </Button>

                                <Button
                                    variant="contained"
                                    color="error"
                                    onClick={() => deleteTask(task.id)}
                                >
                                    Löschen
                                </Button>

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
                Aufgabe bearbeiten
            </DialogTitle>

            <DialogContent>

                <TextField
                    label="Titel"
                    value={editTask?.title || ""}
                    onChange={(e) =>
                        setEditTask({
                            ...editTask,
                            title: e.target.value
                        })
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    label="Beschreibung"
                    value={editTask?.description || ""}
                    onChange={(e) =>
                        setEditTask({
                            ...editTask,
                            description: e.target.value
                        })
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    select
                    label="Status"
                    value={editTask?.status || "OPEN"}
                    onChange={(e) =>
                        setEditTask({
                            ...editTask,
                            status: e.target.value
                        })
                    }
                    fullWidth
                    margin="normal"
                >
                    <MenuItem value="OPEN">OPEN</MenuItem>
                    <MenuItem value="IN_PROGRESS">IN_PROGRESS</MenuItem>
                    <MenuItem value="DONE">DONE</MenuItem>
                </TextField>

            </DialogContent>

            <DialogActions>
                <Button onClick={() => setOpen(false)}>
                    Abbrechen
                </Button>

                <Button
                    variant="contained"
                    onClick={saveTask}
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
        severity="error"
        variant="filled"
        onClose={() => setOpenError(false)}
    >
        {errorMessage}
    </Alert>
</Snackbar>
    </>
);

}

export default Tasks;
