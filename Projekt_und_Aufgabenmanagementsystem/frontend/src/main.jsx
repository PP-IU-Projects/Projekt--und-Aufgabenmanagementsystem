import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";

/*
 * Einstiegspunkt der React-Anwendung.
 *
 * Hier wird die Hauptkomponente (App)
 * in das HTML-Element mit der ID "root"
 * eingebunden.
 *
 * Der BrowserRouter ermöglicht das
 * clientseitige Routing und den
 * Seitenwechsel innerhalb der Anwendung,
 * ohne dass die Seite neu geladen wird.
 */
ReactDOM.createRoot(document.getElementById("root")).render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
);