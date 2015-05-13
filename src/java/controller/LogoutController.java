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
import service.ILoginService;
import service.LoginService;

/**
 *
 * @author Febo
 */
@WebServlet (name="LogoutController", urlPatterns = {"/logout.do"})
public class LogoutController extends HttpServlet {
    private ILoginService logoutService;
    
    public LogoutController() {
        logoutService = new LoginService();
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
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("JSESSIONID="+cookie.getValue());
                    break;
                }
            }
            for(Cookie cooky : cookies){
                cooky.setValue("");
                cooky.setPath("/");
                cooky.setMaxAge(0);
                response.addCookie(cooky);
            }
        }
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        System.out.println("User="+session.getAttribute("user"));
        if(session.getAttribute("user") != null){
            System.out.println("Session invalidate...");
            session.invalidate();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/login.jsp");
        dispatcher.forward(request,response);
//        response.sendRedirect("views/login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Logout Session";
    }// </editor-fold>

}
