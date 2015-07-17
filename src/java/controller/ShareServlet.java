package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.instances.ShareService;

@WebServlet(name = "ShareServlet", urlPatterns = {"/share.do"})
public class ShareServlet extends HttpServlet {
    private ShareService shareService;

    public ShareServlet() {
        this.shareService = new ShareService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_publication = request.getParameter("id_publication");
        String id_user_share = request.getParameter("id_user");
        String text = request.getParameter("publicationText");
        String destination = request.getParameter("destination");
        
        if(id_publication != null & id_user_share != null & text != null & destination != null ){
            boolean insertOk = shareService.insertShare(Integer.parseInt(id_publication), id_user_share, text, destination);
            if(insertOk){
                response.getWriter().write("{\"mensaje\":\"Se inserto correctamente la publicacion compartida\"}");
            }else{
                response.getWriter().write("{\"error\":\"Error de insercion de la publicacion compartida\"}");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Share Servlet";
    }// </editor-fold>

}
