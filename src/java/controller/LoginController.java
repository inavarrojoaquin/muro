package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dts.UserDTO;
import service.ILoginService;
import service.LoginService;

/**
 *
 * @author Febo
 * Me logueo con un usuario, ingreso al muro(me muestra los datos), presiono Logout y me vuelve al login.
 * Si ingreso con otro usuario ingresa correctamente, pero, si pongo solo el id_usuario y una contraseña cualquiera, muestra los datos
 * del usuario cargado anteriormente.(es como que el usuario no se borra)
 * El logout no esta bien implementado.
 * Si hago un back estando en el muro, no deberia volver al login
 */
@WebServlet (name="LoginController", urlPatterns = {"/login.do"})
public class LoginController extends HttpServlet {
    private ILoginService loginService;
    
    public LoginController() {
        loginService = new LoginService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null){
                System.out.println("Existe usuario redireccionando a muro...");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("muro.jsp");
                dispatcher.forward(request,response);
                //response.sendRedirect("muro.jsp");
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_usuario = request.getParameter("idusuario");
        String password = request.getParameter("password");

        UserDTO userDTO = loginService.login(id_usuario, password);
     
        if (userDTO != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userDTO.getNombre());
            session.setMaxInactiveInterval(-1); //Nunca expira la sesion, unicamente con el boton Logout
            Cookie userName = new Cookie("user", userDTO.getNombre());
            userName.setMaxAge(15*60);
            response.addCookie(userName);
            System.out.println("session: "+session);
            System.out.println("UserDTO nombre: "+userDTO.getNombre());
            request.setAttribute("user", userDTO.getNombre());
            response.sendRedirect("views/muro.jsp");
        } else {
            String error = "No se encontró el usuario";
            request.setAttribute("error", error);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/error.jsp");
            dispatcher.include(request,response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }// </editor-fold>

}
