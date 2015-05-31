<% if(session.getAttribute("user") != null) response.sendRedirect("muro.jsp"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/main.css" >
        <meta name="viewport" content="width=device-width, initial-scale=1">  <%--Boostrap init--%>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> <%--Boostrap--%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> <%--Boostrap--%>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script> <%--Boostrap--%> 
        
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
                    <input type="number" class="form-control" id="usuario" name="idusuario" placeholder="id_usuario" title="Campo obligatorio" required autofocus>
                    <label for="password" class="sr-only"></label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="password" title="Campo obligatorio" required>
                <div class="checkbox">
                    <label><input type="checkbox">Recordarme</label></br>
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
