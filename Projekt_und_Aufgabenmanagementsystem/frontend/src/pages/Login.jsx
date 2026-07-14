import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Alert
} from "@mui/material";

import api from "../services/api";

/*
 * Login Seite
 *
 * Benutzer meldet sich mit
 * E-Mail und Passwort an.
 */
function Login() {

    /*
     * Formulardaten
     */
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    /*
     * Fehlermeldung
     */
    const [error, setError] = useState("");

    /*
     * Navigation nach erfolgreichem Login
     */
    const navigate = useNavigate();

    /*
     * Login durchführen
     */
    const login = () => {

        api.post("/auth/login", {
            email,
            password
        })
        .then((response) => {

            /*
             * JWT speichern
             */
            localStorage.setItem(
                "token",
                response.data.token
            );

            /*
             * Benutzername speichern
             */
            localStorage.setItem(
                "username",
                response.data.username
            );

            /*
             * Rolle speichern
             */
            localStorage.setItem(
                "role",
                response.data.role
            );

            /*
 * Anwendung neu laden,
 * damit die Sidebar die
 * Benutzerrolle neu einliest.
 */
window.location.href = "/";
        })
        .catch(() => {
            setError(
                "Ungültige E-Mail oder Passwort"
            );
        });
    };

    return (
        <Container maxWidth="sm">

            <Paper sx={{ p: 4, mt: 10 }}>

                <Typography
                    variant="h4"
                    gutterBottom
                >
                    Login
                </Typography>

                {
                    error &&
                    <Alert
                        severity="error"
                        sx={{ mb: 2 }}
                    >
                        {error}
                    </Alert>
                }

                <TextField
                    label="E-Mail"
                    value={email}
                    onChange={(e) =>
                        setEmail(e.target.value)
                    }
                    fullWidth
                    margin="normal"
                />

                <TextField
                    label="Passwort"
                    type="password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)
                    }
                    fullWidth
                    margin="normal"
                />

                <Button
                    variant="contained"
                    fullWidth
                    sx={{ mt: 2 }}
                    onClick={login}
                >
                    Anmelden
                </Button>

            </Paper>

        </Container>
    );
}

export default Login;