import { Navigate } from "react-router-dom";

/*
 * Prüft ob ein JWT Token vorhanden ist.
 *
 * Falls nein:
 * -> Weiterleitung zum Login
 *
 * Falls ja:
 * -> Seite anzeigen
 */
function ProtectedRoute({ children }) {

    const token = localStorage.getItem("token");

    if (!token) {
        return <Navigate to="/login" />;
    }

    return children;
}

export default ProtectedRoute;