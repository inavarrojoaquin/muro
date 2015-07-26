<%@page import="service.instances.LoginService"%>
<% if(session.getAttribute("id_user") == null) response.sendRedirect("login.jsp"); %>
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
        <% 
            LoginService loginService = new LoginService();
            String id_user = (String)session.getAttribute("id_user");
            /** Update last Date in database */
            boolean updateOK = loginService.updateDate(id_user); 
        %>
        
        <%-- HEADER --%>
        <header>
            <nav class="navbar navbar-default navbar-static-top navbar-inverse">
                <div class="container">
                    <div class="navbar-header">
                        <a href="#" class="navbar-brand">Muro <span class="small">ubp</span></a>
                    </div>
                    <%-- INIT MENU --%>
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li role="presentation">
                                <button type="button" class="btn navbar-btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Publications <span class="badge" id="publication_count"></span> <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" id="publication_menu"></ul>
                            </li>
                            <li role="presentation"><a href="#" onclick="update();">Update</a></li>
                            <li role="presentation"><a href="logout.do" onclick="clearSessionStorage();">Logout</a></li>
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
                    <div id="insert-form-publication" style="display: none">
                        <ul class="nav nav-tabs">
                            <li role="presentation" class="active"><a href="#tab-inputText" data-toggle="tab">Texto</a></li>
                            <li role="presentation"><a href="#tab-inputLink" data-toggle="tab">Link</a></li>                        
                        </ul>
                    
                        <div class="tab-content form-group">
                            <div id="tab-inputText" class="tab-pane active">
                                <div class="input-group">
                                    <input type="text" class="form-control" id='text-publication-to-insert' data-action='input-publication' placeholder="Escriba una publicacion..." >
                                    <span class="input-group-addon btn btn-primary" data-action='send-publication'>Publicar</span>
                                </div>
                            </div>
                            <div id="tab-inputLink" class="tab-pane">
                                <div class="input-group">
                                    <textarea id='link-publication-to-insert' data-action='input-publication' class="form-control custom-control" rows="2" style="resize:none" placeholder="Aqui links(imagenes, video: Youtube, Vimeo)"></textarea>     
                                    <span class="input-group-addon btn btn-primary" data-action='send-publication'>Publicar</span>
                                </div>
                            </div>
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
