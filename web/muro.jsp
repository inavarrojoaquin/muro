<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        
        <title>Muro</title>
    </head>
    <body>
        <header>
            <h1>MURO</h1>
            <a href="logout.do">Logout</a><br/>
        </header>
        <nav>
            <div id="carreras"><span id="idCarrera">Carrera</span></div>
            <div id="materias"><span id="idMateria">Materia</span></div>
            <div class="error"></div>
        </nav>
        <section>
        </section>
        <footer>
            Copyright &reg; Febo
        </footer>
    </body>
</html>
