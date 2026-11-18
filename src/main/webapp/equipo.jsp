<%-- Pagina del equipo y reparto de trabajo. Autor: Ibai --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setAttribute("tituloPagina", "Equipo");
    String[][] miembros = {
        {"Ibai",  "Vistas e interfaz",     "Páginas JSP, cabecera y pie comunes, hoja de estilos CSS."},
        {"Iker",  "Modelo de datos",       "Clases Tarea y Prioridad, repositorio en memoria (Singleton)."},
        {"David", "Controlador",           "TareaServlet (crear, completar, borrar, filtrar) y validaciones."},
        {"Xabi",  "Configuración y despliegue", "web.xml, filtro UTF-8/log, página de error y script de despliegue."}
    };
%>
<%@ include file="/WEB-INF/vistas/fragmentos/cabecera.jspf" %>

<section class="tarjeta">
    <h2>Grupo 1</h2>
    <p>Reparto del trabajo siguiendo el patrón <strong>MVC</strong> (Modelo - Vista - Controlador):</p>

    <div class="equipo">
        <% for (String[] m : miembros) { %>
            <article class="miembro">
                <div class="avatar"><%= m[0].charAt(0) %></div>
                <h3><%= m[0] %></h3>
                <p class="rol"><%= m[1] %></p>
                <p><%= m[2] %></p>
            </article>
        <% } %>
    </div>
</section>

<%@ include file="/WEB-INF/vistas/fragmentos/pie.jspf" %>
