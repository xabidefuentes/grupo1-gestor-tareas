<%-- VISTA de la lista de tareas (la rellena TareaServlet). Autor: Ibai --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, es.grupo1.tareas.modelo.Tarea, es.grupo1.tareas.modelo.Prioridad, es.grupo1.tareas.util.Html" %>
<%
    request.setAttribute("tituloPagina", "Tareas");
    @SuppressWarnings("unchecked")
    List<Tarea> tareas = (List<Tarea>) request.getAttribute("tareas");
    String filtro = (String) request.getAttribute("filtro");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<%@ include file="/WEB-INF/vistas/fragmentos/cabecera.jspf" %>

<section class="tarjeta">
    <h2>Nueva tarea</h2>

    <% if (mensaje != null) { %><p class="aviso ok"><%= mensaje %></p><% } %>
    <% if (error != null) { %><p class="aviso ko"><%= error %></p><% } %>

    <form class="nueva" action="tareas" method="post">
        <input type="hidden" name="accion" value="crear">
        <input type="hidden" name="filtro" value="<%= Html.escapar(filtro) %>">
        <input type="text" name="titulo" placeholder="¿Qué hay que hacer?" maxlength="80" required>
        <select name="prioridad">
            <% for (Prioridad p : Prioridad.values()) { %>
                <option value="<%= p %>" <%= p == Prioridad.MEDIA ? "selected" : "" %>><%= p.getTexto() %></option>
            <% } %>
        </select>
        <button class="boton" type="submit">Añadir</button>
    </form>
</section>

<section class="tarjeta">
    <div class="cabecera-lista">
        <h2>Lista (<%= request.getAttribute("pendientes") %> pendientes de <%= request.getAttribute("total") %>)</h2>
        <nav class="filtros">
            <a class="<%= "todas".equals(filtro) ? "activo" : "" %>" href="tareas">Todas</a>
            <a class="<%= "pendientes".equals(filtro) ? "activo" : "" %>" href="tareas?filtro=pendientes">Pendientes</a>
            <a class="<%= "completadas".equals(filtro) ? "activo" : "" %>" href="tareas?filtro=completadas">Completadas</a>
        </nav>
    </div>

    <% if (tareas.isEmpty()) { %>
        <p class="vacio">No hay tareas en esta vista.</p>
    <% } else { %>
    <table>
        <thead>
            <tr><th>Estado</th><th>Tarea</th><th>Prioridad</th><th>Creada</th><th></th></tr>
        </thead>
        <tbody>
        <% for (Tarea t : tareas) { %>
            <tr class="<%= t.isCompletada() ? "hecha" : "" %>">
                <td>
                    <form action="tareas" method="post">
                        <input type="hidden" name="accion" value="completar">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <input type="hidden" name="filtro" value="<%= Html.escapar(filtro) %>">
                        <button class="check" type="submit" title="Marcar / desmarcar">
                            <%= t.isCompletada() ? "&#10004;" : "" %>
                        </button>
                    </form>
                </td>
                <td class="titulo"><%= Html.escapar(t.getTitulo()) %></td>
                <td><span class="etiqueta <%= t.getPrioridad().name().toLowerCase() %>"><%= t.getPrioridad().getTexto() %></span></td>
                <td class="fecha"><%= t.getFechaFormateada() %></td>
                <td>
                    <form action="tareas" method="post" onsubmit="return confirm('¿Borrar esta tarea?');">
                        <input type="hidden" name="accion" value="borrar">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <input type="hidden" name="filtro" value="<%= Html.escapar(filtro) %>">
                        <button class="borrar" type="submit" title="Borrar">&#128465;</button>
                    </form>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</section>

<%@ include file="/WEB-INF/vistas/fragmentos/pie.jspf" %>
