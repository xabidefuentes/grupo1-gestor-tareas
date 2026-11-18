package es.grupo1.tareas.modelo;

/**
 * Niveles de prioridad de una tarea.
 *
 * @author Iker
 */
public enum Prioridad {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private final String texto;

    Prioridad(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
