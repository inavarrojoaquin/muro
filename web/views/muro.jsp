<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>Muro!</h1>
        
        <form action="../logout.do" method="post">
            <input type="submit" value="Logout"/>
        </form>
        <form action="login.jsp" method="post">
            <input type="submit" value="Back"/>
        </form>
        
        <%
        //allow access only if session exists
        String user = null;
        System.out.println("UserSession en muro: "+session.getAttribute("user"));
        if(session.getAttribute("user") == null){
            response.sendRedirect("login.jsp");
        }else {
            user = (String) session.getAttribute("user");
        }
        String userName = null;
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
                if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
            }
        }
        System.out.println("Usuario muro: "+user);
        %>
        Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %>
        User <%=user%><br>
        <h3>Usuario: ${user}</h3>
        
    </body>
</html>
