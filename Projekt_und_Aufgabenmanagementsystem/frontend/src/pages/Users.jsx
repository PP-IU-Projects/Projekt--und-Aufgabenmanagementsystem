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
    TextField,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    MenuItem
} from "@mui/material";

function Users() {

    /*
     * Liste aller Benutzer
     */
    const [users, setUsers] = useState([]);
    
    /*
 * Fehlermeldung
 */
const [errorMessage, setErrorMessage] = useState("");
const [openError, setOpenError] = useState(false);
    /*
 * Rolle des aktuell angemeldeten Benutzers
 */
const currentRole = localStorage.getItem("role");
    /*
     * Eingabefelder für neuen Benutzer
     */
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("EMPLOYEE");

    /*
     * Dialog zum Bearbeiten
     */
    const [open, setOpen] = useState(false);
    const [editUser, setEditUser] = useState(null);

    /*
     * Benutzer beim Laden der Seite abrufen
     */
    useEffect(() => {
        loadUsers();
    }, []);

    /*
     * Alle Benutzer vom Backend laden
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
     * Neuen Benutzer erstellen
     */
    const createUser = () => {

        api.post("/users", {
            username,
            email,
            password,
            role
        })
        .then(() => {

            loadUsers();

            /*
             * Formular zurücksetzen
             */
            setUsername("");
            setEmail("");
            setPassword("");
            setRole("EMPLOYEE");
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
     * Benutzer löschen
     */
    const deleteUser = (id) => {

        api.delete("/users/" + id)
            .then(() => {
                loadUsers();
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
     * Dialog zum Bearbeiten öffnen
     */
    const openEditDialog = (user) => {

        setEditUser({
            ...user
        });

        setOpen(true);
    };

    /*
     * Änderungen speichern
     */
    const saveUser = () => {

        api.put(
            "/users/" + editUser.id,
            editUser
        )
        .then(() => {

            setOpen(false);

            loadUsers();
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
 * Nur Administratoren dürfen
 * die Benutzerverwaltung öffnen.
 */
if (currentRole !== "ADMIN") {

    return (

        <Container>

            <Typography
                variant="h4"
                color="error"
            >
                Keine Berechtigung
            </Typography>

            <Typography>
                Diese Seite ist nur für Administratoren verfügbar.
            </Typography>

        </Container>

    );
}
    return (

        <Container>

            {/* Überschrift */}
            <Typography
                variant="h4"
                gutterBottom
            >
                Benutzerverwaltung
            </Typography>

            {/* Formular zum Erstellen */}
            <Paper
                sx={{
                    p: 2,
                    mb: 3
                }}
            >

                <Typography
                    variant="h6"
                    gutterBottom
                >
                    Benutzer erstellen
                </Typography>

                <TextField
                    label="Benutzername"
                    value={username}
                    onChange={(e) =>
                        setUsername(
                            e.target.value
                        )
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    label="E-Mail"
                    value={email}
                    onChange={(e) =>
                        setEmail(
                            e.target.value
                        )
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    label="Passwort"
                    type="password"
                    value={password}
                    onChange={(e) =>
                        setPassword(
                            e.target.value
                        )
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    select
                    label="Rolle"
                    value={role}
                    onChange={(e) =>
                        setRole(
                            e.target.value
                        )
                    }
                    fullWidth
                    margin="normal"
                >
                    <MenuItem value="ADMIN">
                        ADMIN
                    </MenuItem>

                    <MenuItem value="PROJECT_MANAGER">
                        PROJECT_MANAGER
                    </MenuItem>

                    <MenuItem value="EMPLOYEE">
                        EMPLOYEE
                    </MenuItem>

                </TextField>

                <Button
                    variant="contained"
                    sx={{ mt: 2 }}
                    onClick={createUser}
                >
                    Benutzer erstellen
                </Button>

            </Paper>

            {/* Tabelle aller Benutzer */}
            <TableContainer
                component={Paper}
            >
                <Table>

                    <TableHead>

                        <TableRow>

                            <TableCell>
                                ID
                            </TableCell>

                            <TableCell>
                                Benutzername
                            </TableCell>

                            <TableCell>
                                E-Mail
                            </TableCell>

                            <TableCell>
                                Rolle
                            </TableCell>

                            <TableCell>
                                Aktionen
                            </TableCell>

                        </TableRow>

                    </TableHead>

                    <TableBody>

                        {users.map((user) => (

                            <TableRow
                                key={user.id}
                            >

                                <TableCell>
                                    {user.id}
                                </TableCell>

                                <TableCell>
                                    {user.username}
                                </TableCell>

                                <TableCell>
                                    {user.email}
                                </TableCell>

                                <TableCell>
                                    {user.role}
                                </TableCell>

                                <TableCell>

                                    <Button
                                        variant="contained"
                                        sx={{
                                            mr: 1
                                        }}
                                        onClick={() =>
                                            openEditDialog(
                                                user
                                            )
                                        }
                                    >
                                        Bearbeiten
                                    </Button>

                                    <Button
                                        variant="contained"
                                        color="error"
                                        onClick={() =>
                                            deleteUser(
                                                user.id
                                            )
                                        }
                                    >
                                        Löschen
                                    </Button>

                                </TableCell>

                            </TableRow>

                        ))}

                    </TableBody>

                </Table>

            </TableContainer>

            {/* Dialog zum Bearbeiten */}
            <Dialog
                open={open}
                onClose={() =>
                    setOpen(false)
                }
            >

                <DialogTitle>
                    Benutzer bearbeiten
                </DialogTitle>

                <DialogContent>

                    <TextField
                        label="Benutzername"
                        value={
                            editUser?.username || ""
                        }
                        onChange={(e) =>
                            setEditUser({
                                ...editUser,
                                username:
                                    e.target.value
                            })
                        }
                        fullWidth
                        margin="normal"
                    />

                    <TextField
                        label="E-Mail"
                        value={
                            editUser?.email || ""
                        }
                        onChange={(e) =>
                            setEditUser({
                                ...editUser,
                                email:
                                    e.target.value
                            })
                        }
                        fullWidth
                        margin="normal"
                    />

                    <TextField
                        select
                        label="Rolle"
                        value={
                            editUser?.role ||
                            "EMPLOYEE"
                        }
                        onChange={(e) =>
                            setEditUser({
                                ...editUser,
                                role:
                                    e.target.value
                            })
                        }
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="ADMIN">
                            ADMIN
                        </MenuItem>

                        <MenuItem value="PROJECT_MANAGER">
                            PROJECT_MANAGER
                        </MenuItem>

                        <MenuItem value="EMPLOYEE">
                            EMPLOYEE
                        </MenuItem>

                    </TextField>

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
                        onClick={saveUser}
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
        </Container>
    );
}

export default Users;