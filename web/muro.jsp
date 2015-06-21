<% if(session.getAttribute("id_user") == null) response.sendRedirect("login.jsp"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%--<link rel="stylesheet" href="css/my-css/main.css">--%>
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <script src="js/my-script/script.js"  type="text/javascript"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        
        <title>Muro</title>
    </head>
    <body>
        <header>
            <h1>MURO</h1>
            <input type="hidden" name="id_user" value="${id_user}" />
            <input type="hidden" name="userName" value="${userName}" />
            <a href="logout.do">Logout</a><br/>
        </header>
        <nav>
            <a href="https://www.facebook.com/sharer/sharer.php?u=http://localhost:8080/muro/login.jsp" target="_blank">
               <img src="https://lh3.googleusercontent.com/-H8xMuAxM-bE/UefWwJr2vwI/AAAAAAAAEdY/N5I41q19KMk/s32-no/facebook.png">
            </a>
            <a href="http://www.twitter.com/home?status=http://localhost:8080/muro/login.jsp" target="_blank">
               <img src="https://lh5.googleusercontent.com/-xZVxH6CsUaQ/UefWwgi8o3I/AAAAAAAAEdk/reo5XS6z8-8/s32-no/twitter.png">
            </a>
            <div id="carreras"><span id="idCarrera">Carrera</span></div>
            <div id="materias"><span id="idMateria">Materia</span></div>
            <div class="error"></div>
            <div class="success"></div>
        </nav>
        <section>
            <div id="insertPublication">
                <form>
                    <p>Publicar contenido</p>
                    <textarea id="contentPublication"></textarea><br>
                    <input id="insertContent" type="button" value="Insertar"/>
                </form>
            </div>
            <div id="publications"></div>
        </section>
        <footer>
            Copyright &reg; Febo
        </footer>
    </body>
</html>
