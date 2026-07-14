/*
 * Behandelt Fehler von API-Aufrufen.
 */
const handleApiError = (error, setErrorMessage, setOpenError) => {

    console.error(error);

    setErrorMessage(
        error.response?.data?.message ||
        "Ein unerwarteter Fehler ist aufgetreten."
    );

    setOpenError(true);
};

export default handleApiError;