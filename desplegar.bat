@echo off
REM ============================================================
REM  Despliegue del Gestor de Tareas (Grupo 1) en Apache Tomcat
REM  1. Borra la version anterior desplegada
REM  2. Copia las paginas web (JSP, CSS, web.xml)
REM  3. Compila las clases Java dentro de WEB-INF\classes
REM  Autor: Xabi
REM ============================================================

set PROYECTO=%~dp0
set TOMCAT=%PROYECTO%..\apache-tomcat-11.0.26
set APP=grupo1
set DESTINO=%TOMCAT%\webapps\%APP%

echo [1/3] Limpiando despliegue anterior...
if exist "%DESTINO%" rmdir /s /q "%DESTINO%"

echo [2/3] Copiando ficheros web...
xcopy "%PROYECTO%src\main\webapp" "%DESTINO%\" /E /I /Y /Q >nul

echo [3/3] Compilando clases Java...
mkdir "%DESTINO%\WEB-INF\classes" 2>nul
dir /s /b "%PROYECTO%src\main\java\*.java" > "%TEMP%\grupo1_fuentes.txt"
javac --release 17 -encoding UTF-8 -cp "%TOMCAT%\lib\servlet-api.jar" -d "%DESTINO%\WEB-INF\classes" @"%TEMP%\grupo1_fuentes.txt"

if errorlevel 1 (
    echo.
    echo ERROR al compilar. Revisa los mensajes de arriba.
) else (
    echo.
    echo Desplegado correctamente.
    echo Arranca Tomcat y abre: http://localhost:8080/%APP%/
)
pause
