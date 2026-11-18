package es.grupo1.tareas.filtro;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Filtro que se ejecuta ANTES de cada peticion:
 *  1. Fuerza la codificacion UTF-8 (tildes y enes correctas).
 *  2. Escribe en el log de Tomcat cada peticion y cuanto ha tardado.
 *
 * @author Xabi
 */
@WebFilter("/*")
public class RegistroFiltro implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        long inicio = System.currentTimeMillis();
        chain.doFilter(request, response); // continua hacia el Servlet/JSP
        long tiempo = System.currentTimeMillis() - inicio;

        HttpServletRequest req = (HttpServletRequest) request;
        request.getServletContext().log("[Grupo1] " + req.getMethod() + " "
                + req.getRequestURI() + " (" + tiempo + " ms)");
    }
}
