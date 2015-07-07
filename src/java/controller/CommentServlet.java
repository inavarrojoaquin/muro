package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dto.CommentDTO;
import service.instances.CommentService;

@WebServlet(name = "CommentServlet", urlPatterns = {"/comment.do"})
public class CommentServlet extends HttpServlet {
    private CommentService commentService;

    public CommentServlet() {
        this.commentService = new CommentService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_publication = request.getParameter("id_publication");
        
        Type listType = new TypeToken<List<CommentDTO>>(){}.getType();        
        Gson gson = new GsonBuilder().serializeNulls().create();
        String representacionJSON;
        
        if(id_publication != null){
            List<CommentDTO> commentList = commentService.getCommentsByPublication(Integer.parseInt(id_publication));
            if (commentList != null) {
                representacionJSON = gson.toJson(commentList, listType);
            }else {
                representacionJSON = "{ \"error\":\"La publicacion seleccionada no tiene comentarios para mostrar\" }";
            }
            response.getWriter().write(representacionJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String text = request.getParameter("text");
        String id_user = request.getParameter("id_user");
        String id_publication = request.getParameter("id_publication");
        String _method = request.getParameter("method");
        
        if(_method != null){
            doDelete(request, response);
        }
        
        if(text != null && id_user != null && id_publication != null){
            boolean insertOk = commentService.insertComment(text, id_user, Integer.parseInt(id_publication));
            if(insertOk){
                response.getWriter().write("{\"mensaje\":\"Comentario insertado correctamente\"}");
            }else {
                response.getWriter().write("{\"error\":\"Error de insercion del comentario\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_comment = request.getParameter("id_comment");
        
        if(id_comment != null){
            boolean deleteOk = commentService.deleteComment(Integer.parseInt(id_comment));
            if(deleteOk){
                response.getWriter().write("{\"mensaje\":\"Comentario eliminado correctamente\"}");
            }else{
                response.getWriter().write("{\"error\":\"Error al eliminar comentario\"}");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Comment Servlet";
    }// </editor-fold>

}
