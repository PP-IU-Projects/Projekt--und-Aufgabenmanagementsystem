import {
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemText,
    Toolbar
} from "@mui/material";

import { Link } from "react-router-dom";

const drawerWidth = 240;

function Sidebar() {

    /*
     * Rolle des aktuell angemeldeten Benutzers
     */
    const role = localStorage.getItem("role");

    /*
     * Menüeinträge
     */
    const menuItems = [
        { text: "Dashboard", path: "/" },
        { text: "Benutzer", path: "/users" },
        { text: "Projekte", path: "/projects" },
        { text: "Aufgaben", path: "/tasks" },
        { text: "Login", path: "/login" }
    ];

    /*
     * Benutzerverwaltung nur für Administratoren anzeigen
     */
    const visibleMenuItems = menuItems.filter(item => {

        if (item.text === "Benutzer" && role !== "ADMIN") {
            return false;
        }

        return true;
    }); 

return (
        <Drawer
            variant="permanent"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                "& .MuiDrawer-paper": {
                    width: drawerWidth,
                    boxSizing: "border-box"
                }
            }}
        >
            <Toolbar />

            <List>
                {visibleMenuItems.map((item) => (
                    <ListItem key={item.text} disablePadding>
                        <ListItemButton
                            component={Link}
                            to={item.path}
                        >
                            <ListItemText primary={item.text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Drawer>
    );
}

export default Sidebar;