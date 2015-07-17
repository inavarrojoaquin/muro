<% if(session.getAttribute("id_user") == null) response.sendRedirect("login.jsp"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <title>Muro</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>   <%--Boostrap css--%>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body id="page-top">
        <header>
            <nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
                <div class="container-fluid">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="navbar-header">
                            <a href="#" class="navbar-brand">Muro <span class="small">ubp</span></a>
                        </div>
                        <%-- Inicia menu --%>
                        <div class="collapse navbar-collapse">
                            <ul class="nav navbar-nav navbar-right">
                                <li><a href="#page-top" class="">Inicio</a></li>
                                <li><a href="logout.do" class="">Logout</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </nav>
            <input type="hidden" name="id_user" value="${id_user}" />
            <input type="hidden" name="userName" value="${userName}" />
            <input type="hidden" name="id_role" value="${id_role}" />
        </header>
        
        <section class="main container">
            
        </section>
        
        <footer></footer>
        
        <div class="container">
            <div class="row">
                <nav class="col-md-3 color1">
                    <div id="carreras"><span id="idCarrera">Carrera</span></div>
                    <div id="materias"><span id="idMateria">Materia</span></div>
                    <div class="success"></div>
                    <div class="error"></div>
                </nav>
                <section class='col-md-9 color2'>
                    <div id="insertPublication" style='display:none;'>
                        <form class="form-horizontal">
                            <div class="form-group">
                                <div class="col-md-5 col-md-offset-2">
                                    <textarea id="contentPublication" class="form-control" placeholder="Escribe una publicacion"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-2 col-md-offset-2">
                                    <input id="insertContent" type="button" class="btn btn-primary" value="Publicar"/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="publications"></div>
                </section>
            </div>
        </div>
        
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script> <%--Boostrap js--%>
        <script src="js/script.js"  type="text/javascript"></script>
    </body>
</html>
