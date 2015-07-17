<% if(session.getAttribute("id_user") != null) response.sendRedirect("muro.jsp"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <title>Login</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>   <%--Boostrap css--%>
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body style="background-color: darkcyan">
        <section>
            <div class="container">
                <div class="login-box jumbotron col-md-4 col-md-offset-4">
                    <form id="login" role="form" name="login"  method="post" action="login.do">
                    <fieldset>
                        <legend class="text-center">Ingreso al MURO</legend>
                        <div class="form-group">
                            <label for="usuario" class="sr-only"></label>
                            <input type="number" class="form-control" id="id_user" name="id_user" placeholder="id_usuario" title="Campo obligatorio" required autofocus />
                            <label for="password" class="sr-only"></label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="password" title="Campo obligatorio" required />
                        </div>
                        <input type="submit" class="btn btn-lg btn-block btn-info col-md-4" name="submit" value="Ingresar" />        
                    </fieldset>
                    </form>
                </div>
            </div>
        </section>
        <footer class='text-center'>
            <div class="container">
                <p>By &reg;Febo</p>
            </div>
        </footer>
        
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script> <%--Boostrap js--%>
    </body>
</html>
