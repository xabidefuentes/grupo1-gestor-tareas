package es.grupo1.tareas.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Almacen de tareas EN MEMORIA (sin base de datos).
 * Los datos se pierden al reiniciar Tomcat.
 *
 * Es un Singleton: existe una unica lista compartida por todos los usuarios.
 * Usa colecciones concurrentes porque Tomcat atiende varias peticiones a la vez.
 *
 * @author Iker
 */
public class RepositorioTareas {

    private static final RepositorioTareas INSTANCIA = new RepositorioTareas();

    private final Map<Integer, Tarea> tareas = new ConcurrentHashMap<>();
    private final AtomicInteger contador = new AtomicInteger(0);

    private RepositorioTareas() {
        // Datos de ejemplo para que la lista no aparezca vacia
        crear("Instalar Apache Tomcat 11", Prioridad.ALTA).alternarCompletada();
        crear("Crear el Servlet controlador", Prioridad.ALTA);
        crear("Diseñar las vistas JSP", Prioridad.MEDIA);
        crear("Preparar la presentación", Prioridad.BAJA);
    }

    public static RepositorioTareas getInstancia() {
        return INSTANCIA;
    }

    public Tarea crear(String titulo, Prioridad prioridad) {
        int id = contador.incrementAndGet();
        Tarea tarea = new Tarea(id, titulo, prioridad);
        tareas.put(id, tarea);
        return tarea;
    }

    /** Devuelve las tareas filtradas: "pendientes", "completadas" o todas. */
    public List<Tarea> listar(String filtro) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea t : tareas.values()) {
            if ("pendientes".equals(filtro) && t.isCompletada()) continue;
            if ("completadas".equals(filtro) && !t.isCompletada()) continue;
            resultado.add(t);
        }
        // Primero por prioridad (ALTA, MEDIA, BAJA) y luego por orden de creacion
        resultado.sort((a, b) -> a.getPrioridad() != b.getPrioridad()
                ? a.getPrioridad().compareTo(b.getPrioridad())
                : Integer.compare(a.getId(), b.getId()));
        return resultado;
    }

    public void alternarCompletada(int id) {
        Tarea t = tareas.get(id);
        if (t != null) {
            synchronized (t) {
                t.alternarCompletada();
            }
        }
    }

    public void borrar(int id) {
        tareas.remove(id);
    }

    public long contarPendientes() {
        return tareas.values().stream().filter(t -> !t.isCompletada()).count();
    }

    public int contarTotal() {
        return tareas.size();
    }
}
