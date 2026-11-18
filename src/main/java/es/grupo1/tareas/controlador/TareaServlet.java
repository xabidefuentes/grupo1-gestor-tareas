package es.grupo1.tareas.controlador;

import java.io.IOException;

import es.grupo1.tareas.modelo.Prioridad;
import es.grupo1.tareas.modelo.RepositorioTareas;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * CONTROLADOR de la aplicacion (patron MVC).
 *
 *  GET  /tareas?filtro=...          -> muestra la lista (vista tareas.jsp)
 *  POST /tareas  accion=crear       -> crea una tarea
 *  POST /tareas  accion=completar   -> marca/desmarca una tarea
 *  POST /tareas  accion=borrar      -> elimina una tarea
 *
 * Tras cada POST se redirige al GET (patron Post-Redirect-Get) para que
 * al recargar la pagina no se repita la accion.
 *
 * @author David
 */
@WebServlet("/tareas")
public class TareaServlet extends HttpServlet {

    private static final int MAX_TITULO = 80;

    private final RepositorioTareas repositorio = RepositorioTareas.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filtro = req.getParameter("filtro");
        if (filtro == null) {
            filtro = "todas";
        }

        // Pasamos los datos a la vista
        req.setAttribute("filtro", filtro);
        req.setAttribute("tareas", repositorio.listar(filtro));
        req.setAttribute("pendientes", repositorio.contarPendientes());
        req.setAttribute("total", repositorio.contarTotal());

        // Mensaje "flash": se muestra una sola vez y se borra de la sesion
        HttpSession sesion = req.getSession();
        req.setAttribute("mensaje", sesion.getAttribute("mensaje"));
        req.setAttribute("error", sesion.getAttribute("error"));
        sesion.removeAttribute("mensaje");
        sesion.removeAttribute("error");

        req.getRequestDispatcher("/WEB-INF/vistas/tareas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accion = req.getParameter("accion");
        HttpSession sesion = req.getSession();

        if ("crear".equals(accion)) {
            crear(req, sesion);
        } else if ("completar".equals(accion)) {
            Integer id = leerId(req);
            if (id != null) repositorio.alternarCompletada(id);
        } else if ("borrar".equals(accion)) {
            Integer id = leerId(req);
            if (id != null) {
                repositorio.borrar(id);
                sesion.setAttribute("mensaje", "Tarea eliminada.");
            }
        }

        // Volvemos a la lista manteniendo el filtro (solo valores conocidos)
        String filtro = req.getParameter("filtro");
        boolean filtroValido = "pendientes".equals(filtro) || "completadas".equals(filtro);
        resp.sendRedirect("tareas" + (filtroValido ? "?filtro=" + filtro : ""));
    }

    private void crear(HttpServletRequest req, HttpSession sesion) {
        String titulo = req.getParameter("titulo");
        titulo = (titulo == null) ? "" : titulo.trim();

        // Validaciones
        if (titulo.isEmpty()) {
            sesion.setAttribute("error", "El título no puede estar vacío.");
            return;
        }
        if (titulo.length() > MAX_TITULO) {
            sesion.setAttribute("error", "El título no puede superar " + MAX_TITULO + " caracteres.");
            return;
        }

        Prioridad prioridad;
        try {
            prioridad = Prioridad.valueOf(req.getParameter("prioridad"));
        } catch (Exception e) {
            prioridad = Prioridad.MEDIA;
        }

        repositorio.crear(titulo, prioridad);
        sesion.setAttribute("mensaje", "Tarea creada correctamente.");
    }

    private Integer leerId(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
