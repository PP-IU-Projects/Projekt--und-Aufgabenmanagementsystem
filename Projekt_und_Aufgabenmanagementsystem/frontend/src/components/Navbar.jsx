import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button
} from "@mui/material";

import { useNavigate } from "react-router-dom";

function Navbar() {

    /*
     * React Router Navigation.
     *
     * Wird verwendet um nach dem Logout
     * automatisch zur Login Seite zu wechseln.
     */
    const navigate = useNavigate();

    /*
     * Benutzerinformationen aus dem Browser laden.
     *
     * Diese Werte werden nach dem Login
     * im LocalStorage gespeichert.
     */
    const username =
        localStorage.getItem("username");

    const role =
        localStorage.getItem("role");

    /*
     * Logout Funktion.
     *
     * Entfernt alle gespeicherten
     * Login Informationen und
     * leitet zurück zur Login Seite.
     */
    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("username");
        localStorage.removeItem("role");

        navigate("/login");
    };

    return (

        /*
         * Oberste Navigationsleiste
         */
        <AppBar
            position="fixed"
            sx={{
                zIndex: 1201
            }}
        >
            <Toolbar>

                {/* Titel der Anwendung */}
                <Typography
                    variant="h6"
                    sx={{
                        flexGrow: 1
                    }}
                >
                    Projektmanagementsystem
                </Typography>

                {/* Benutzerinformationen */}
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 2
                    }}
                >

                    {/* Benutzername anzeigen */}
                    <Typography>
                        {username}
                    </Typography>

                    {/* Rolle anzeigen */}
                    <Typography>
                        ({role})
                    </Typography>

                    {/* Logout Button */}
                    <Button
                        color="inherit"
                        variant="outlined"
                        onClick={logout}
                    >
                        Logout
                    </Button>

                </Box>

            </Toolbar>
        </AppBar>
    );
}

export default Navbar;