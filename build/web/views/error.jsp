<% 
    String error = (String)request.getAttribute("error");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Se ha producido un error: ${error}</h1>
        <h3><a href="views/login.jsp">Volver</a></h3>
    </body>
</html>
