package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dto.UserDTO;
import service.ILoginService;
import service.instances.LoginService;

@WebServlet (name="LoginController", urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {
    private ILoginService loginService;
    private HttpSession session;
    
    public LoginServlet() {
        loginService = new LoginService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("muro.jsp");
            dispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password");

        session = request.getSession();
     
            session.setAttribute("id_user", userDTO.getIdUsuario());
            session.setAttribute("id_rol", userDTO.getIdRol());
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/muro.jsp");
//            dispatcher.forward(request,response);
            response.sendRedirect("muro.jsp");
        } else {
            String error = "No se encontro el usuario";
            session.setAttribute("error", error);
            response.sendRedirect("error.jsp");
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
//            dispatcher.include(request,response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }// </editor-fold>

}
