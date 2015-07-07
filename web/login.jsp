<% if(session.getAttribute("id_user") != null) response.sendRedirect("muro.jsp"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/my-css/main.css" >
        <script src="js/jquery.2.1.4.js" type="text/javascript"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css"/>

        <title>Login</title>
    </head>
    <body>
        <header>
        </header>
        <section>
            <div class="container">
            <div class="jumbotron col-md-4 col-md-offset-4">
                <form id="login" role="form" name="login"  method="post" action="login.do">
                <fieldset>
                <legend>Ingreso al MURO</legend>
                    <label for="usuario" class="sr-only"></label>
                    <input type="number" class="form-control" id="id_user" name="id_user" placeholder="id_usuario" title="Campo obligatorio" required autofocus />
                    <label for="password" class="sr-only"></label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="password" title="Campo obligatorio" required />
                <div class="checkbox">
                    <label><input type="checkbox" />Recordarme</label></br>
                </div>
                    <input type="submit" class="btn btn-lg btn-block btn-info col-md-4" name="submit" value="Ingresar" />        
                </fieldset>
                </form>
            </div>
            </div>
        </section>
        <footer>
            <a href="">By &reg;Febo</a>
        </footer>
    </body>
</html>
