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
        <%-- HEADER --%>
        <header>
            <nav class="navbar navbar-default navbar-static-top navbar-inverse">
                <div class="container">
                    <div class="navbar-header">
                        <a href="#" class="navbar-brand">Muro <span class="small">ubp</span></a>
                    </div>
                    <%-- Inicia Menu --%>
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li role="presentation"><a href="#page-top">Inicio</a></li>
                            <li role="presentation"><a href="logout.do">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <input type="hidden" name="id_user" value="${id_user}" >
            <input type="hidden" name="userName" value="${userName}" >
            <input type="hidden" name="id_role" value="${id_role}" >
        </header>
        <%-- MAIN WINDOW --%>
        <section class="main container">
            <div class="row">
                <%-- ASIDE --%>
                <aside class="col-md-3">
                    <%-- CAREERS --%>
                    <div class="list-group" id="careers">
                        <a href="#" class="list-group-item list-group-item-heading active">Carreras</a>
                        <div id="career-list"></div>
                    </div>
                    <%-- SUBJECTS --%>
                    <div class="list-group" id="subjects">
                        <a href="#" class="list-group-item list-group-item-heading active">Materias</a>
                        <div id="subject-list"></div>    
                    </div>
                    <%-- ERROR-SUCCESS ALERT --%>
                    <div id="alert-error-success" tabindex="0"></div> <%-- tabindex='0' para poder dar foco a un div --%>
                    
                </aside>
                <%-- MAIN WALL --%>
                <section class="col-md-7">
                    <%-- INSERT PUBLICATIONS --%>
                    <div class="form-group" id="insert-form-publication" style="display: none">
                        <div class="input-group">
                            <input type="text" id="text-publication-to-insert" class="form-control" placeholder="Escriba una publicacion..." >
                            <textarea id="link-publication-to-insert" class="form-control custom-control" rows="2" style="resize:none" placeholder="Aqui links(imagenes, video: Youtube, Vimeo)"></textarea>     
                            <span id="send-publication" class="input-group-addon btn btn-primary">Publicar</span>
                        </div>
                    </div>
                    <%-- PUBLICATIONS --%>
                    <div id="publications"></div>
                </section>
            </div>
        </section>
        
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script> <%--Boostrap js--%>
        <script src="js/script.js"  type="text/javascript"></script>
    </body>
</html>
