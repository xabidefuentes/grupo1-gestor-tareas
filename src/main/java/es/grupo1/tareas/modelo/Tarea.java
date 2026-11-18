package es.grupo1.tareas.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una tarea de la lista (clase del Modelo).
 *
 * @author Iker
 */
public class Tarea {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private final String titulo;
    private final Prioridad prioridad;
    private final LocalDateTime fechaCreacion;
    private boolean completada;

    public Tarea(int id, String titulo, Prioridad prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();
        this.completada = false;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public String getFechaFormateada() {
        return fechaCreacion.format(FORMATO);
    }

    public boolean isCompletada() {
        return completada;
    }

    /** Cambia la tarea de pendiente a completada y viceversa. */
    public void alternarCompletada() {
        completada = !completada;
    }
}
