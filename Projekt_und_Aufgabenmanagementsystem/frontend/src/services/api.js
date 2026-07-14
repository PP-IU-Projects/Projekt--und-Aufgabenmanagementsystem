import axios from "axios";

/*
 * Zentrale Axios Konfiguration
 *
 * Alle Anfragen des Frontends laufen über diese Datei.
 *
 * Vorteile:
 * - zentrale Backend URL
 * - JWT Token wird automatisch mitgesendet
 * - keine Wiederholungen im Code
 */

const api = axios.create({

    /*
     * Basis URL des Spring Boot Backends
     */
    baseURL: "http://localhost:8080/api"
});

/*
 * Axios Interceptor
 *
 * Wird vor jeder Anfrage ausgeführt.
 *
 * Falls ein JWT Token im LocalStorage vorhanden ist,
 * wird dieser automatisch in den Authorization Header
 * eingefügt.
 *
 * Beispiel:
 *
 * Authorization: Bearer eyJhbGc...
 */
api.interceptors.request.use(

    (config) => {

        /*
         * JWT Token aus dem Browser lesen
         */
        const token = localStorage.getItem("token");

        /*
         * Token vorhanden -> Header setzen
         */
        if (token) {

            config.headers.Authorization =
                `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

export default api;