<%-- Pagina de error (404, 500...). Autor: Xabi --%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<% request.setAttribute("tituloPagina", "Error"); %>
<%@ include file="/WEB-INF/vistas/fragmentos/cabecera.jspf" %>

<section class="tarjeta centrado">
    <h2>Vaya... algo ha salido mal</h2>
    <p class="codigo-error"><%= response.getStatus() %></p>
    <p>
        <%= response.getStatus() == 404
                ? "La página que buscas no existe."
                : "Se ha producido un error interno en el servidor." %>
    </p>
    <a class="boton" href="<%= request.getContextPath() %>/">Volver al inicio</a>
</section>

<%@ include file="/WEB-INF/vistas/fragmentos/pie.jspf" %>
