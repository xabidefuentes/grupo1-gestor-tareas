package es.grupo1.tareas.util;

/**
 * Utilidad para escapar texto antes de mostrarlo en HTML.
 * Evita que un usuario inyecte codigo (ataque XSS) escribiendo
 * por ejemplo "<script>" en el titulo de una tarea.
 *
 * @author David
 */
public final class Html {

    private Html() {
    }

    public static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
