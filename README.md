# Gestor de Tareas · Grupo 1

Aplicación web sencilla desarrollada con **Java Jakarta EE** (Servlets, JSP y Filtros)
y desplegada en **Apache Tomcat 11**. No usa base de datos: las tareas se guardan en memoria.

## Integrantes y reparto del trabajo

| Miembro | Parte | Ficheros |
|---------|-------|----------|
| **Xabi**  | Configuración y despliegue | `web.xml`, `RegistroFiltro.java`, `error.jsp`, `desplegar.bat`, `README.md` |
| **Iker**  | Modelo (datos en memoria)  | `Tarea.java`, `Prioridad.java`, `RepositorioTareas.java` |
| **David** | Controlador                | `TareaServlet.java`, `Html.java` |
| **Ibai**  | Vista (interfaz)           | `index.jsp`, `tareas.jsp`, `equipo.jsp`, `cabecera.jspf`, `pie.jspf`, `estilos.css` |

## Funcionalidades

- Crear tareas con prioridad (Alta, Media o Baja).
- Marcar o desmarcar una tarea como completada.
- Borrar tareas (pide confirmación).
- Filtrar entre todas, pendientes y completadas.
- Validar el título (no vacío, máximo 80 caracteres) y protegerlo contra XSS.
- Páginas de error personalizadas (404 y 500).
- Registrar en el log de Tomcat cada petición y su tiempo de respuesta.

## Arquitectura (MVC)

```
Navegador ──► RegistroFiltro ──► TareaServlet ──► RepositorioTareas
                (UTF-8 + log)     (Controlador)     (Modelo, en memoria)
                                       │
                                       ▼
                                   tareas.jsp (Vista)
```

1. El navegador hace una petición a `/tareas`.
2. El **filtro** fija UTF-8 y mide el tiempo.
3. El **Servlet** lee los parámetros, valida y pide o modifica los datos en el **repositorio**.
4. El Servlet pasa los datos a la **JSP**, que genera el HTML.
5. Después de cada `POST` se redirige a un `GET` (*Post-Redirect-Get*), para que recargar la página no repita la acción.

## Estructura

```
miapp/
├── desplegar.bat                      Compila y copia la app a Tomcat
└── src/main/
    ├── java/es/grupo1/tareas/
    │   ├── modelo/        Tarea, Prioridad, RepositorioTareas
    │   ├── controlador/   TareaServlet
    │   ├── filtro/        RegistroFiltro
    │   └── util/          Html
    └── webapp/
        ├── index.jsp      Página de inicio
        ├── equipo.jsp     Página del equipo
        ├── error.jsp      Página de error
        ├── css/estilos.css
        └── WEB-INF/
            ├── web.xml    Descriptor de despliegue
            └── vistas/    tareas.jsp y fragmentos comunes (no accesibles desde la URL)
```

## Cómo desplegar

Requisitos: JDK 17 o superior y Apache Tomcat 11 en `..\apache-tomcat-11.0.26`.

1. Ejecuta `desplegar.bat`. Compila las clases y copia todo a `webapps\grupo1`.
2. Arranca Tomcat con `bin\levantar_tomcat.bat`.
3. Abre <http://localhost:8080/grupo1/>.

> Las tareas se pierden al reiniciar Tomcat porque no hay base de datos.
