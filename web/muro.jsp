<% if(session.getAttribute("user") == null) response.sendRedirect("login.jsp"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%--<link rel="stylesheet" href="style/main.css">--%>
        <script src="http://code.jquery.com/jquery-2.1.3.min.js" type="text/javascript"></script>
        <script src="js/script.js"  type="text/javascript"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> <%--Boostrap--%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> <%--Boostrap--%>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script> <%--Boostrap--%> 
        
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
            <p id="id_muro"></p>
            <article>
                <h1>Publication 1</h1>
                <p id="comment">Comentarios para la publicacion 1</p>
            </article>
            <article>
                <h1>Publication 2</h1>
                <p>Comentarios para la publicacion 2</p>
             </article>
        </section>
        <footer>
            Copyright &reg; Febo
        </footer>
    </body>
</html>
