<%-- Pagina de inicio. Autor: Ibai --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="es.grupo1.tareas.modelo.RepositorioTareas" %>
<%
    request.setAttribute("tituloPagina", "Inicio");
    RepositorioTareas repo = RepositorioTareas.getInstancia();
%>
<%@ include file="/WEB-INF/vistas/fragmentos/cabecera.jspf" %>

<section class="tarjeta centrado">
    <h1>Gestor de Tareas</h1>
    <p>Aplicación web desarrollada con <strong>Java Jakarta EE</strong> (Servlets, JSP y Filtros)
       y desplegada en <strong>Apache Tomcat</strong>.</p>
    <p>Los datos se guardan en memoria: no se usa base de datos.</p>

    <div class="resumen">
        <div><span><%= repo.contarTotal() %></span>tareas</div>
        <div><span><%= repo.contarPendientes() %></span>pendientes</div>
    </div>

    <a class="boton" href="tareas">Ver mis tareas</a>
</section>

<%@ include file="/WEB-INF/vistas/fragmentos/pie.jspf" %>
