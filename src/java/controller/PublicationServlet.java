package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import static java.lang.System.console;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dto.LikeDTO;
import model.dto.PublicationDTO;
import service.instances.LikeService;
import service.instances.PublicationService;

@WebServlet(name = "PublicationServlet", urlPatterns = {"/publication.do"})
public class PublicationServlet extends HttpServlet {
    private PublicationService publicationService;
    private LikeService likeService;

    public PublicationServlet() {
        this.publicationService = new PublicationService();
        this.likeService = new LikeService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_wall = request.getParameter("id_wall");
        String id_user = request.getParameter("id_user");
        String endDatePublication = request.getParameter("endDatePublication");
        
        Type listType = new TypeToken<List<PublicationDTO>>(){}.getType();        
        Gson gson = new GsonBuilder().serializeNulls().create();
        String representacionJSON;
        
        if(id_wall != null && id_user != null){
            List<PublicationDTO> publicationList = publicationService.getPublications(Short.parseShort(id_wall));
            if (publicationList != null) {
                List<LikeDTO> listaLike = likeService.getUserLike(id_user);
                if(listaLike != null){
                    for (PublicationDTO next : publicationList) {
                        for(LikeDTO nextLike : listaLike){
                            if(next.getId_publicacion() == nextLike.getId_publicacion()){
                                next.setUserLike("Esto te gusta");
                                break;
                            }
                        }
                    }
                }
                representacionJSON = gson.toJson(publicationList, listType);
            }else {
                representacionJSON = "{ \"error\":\"La materia seleccionada no tiene publicaciones para mostrar\" }";
            }
            response.getWriter().write(representacionJSON);
        }
        if(id_wall != null && endDatePublication != null){
            List<PublicationDTO> top5PublicationList = publicationService.getPublicationsBeforeDate(Short.parseShort(id_wall), endDatePublication);
            if(top5PublicationList != null){
                representacionJSON = gson.toJson(top5PublicationList, listType);
            }else {
                representacionJSON = "{ \"error\":\"No existen nuevas publicaciones\" }";
            }
            response.getWriter().write(representacionJSON);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_wall = request.getParameter("id_wall");
        String text = request.getParameter("text");
        String id_user = request.getParameter("id_user");
        String _method = request.getParameter("method");
        
        if(_method != null){
            if(_method.equals("put")){
                doPut(request, response);
            }else{
                doDelete(request, response);
            }
        }else if(id_wall != null && text != null && id_user != null){
            boolean insertOk = publicationService.insertPublication(text, Short.parseShort(id_wall), id_user);
            if(insertOk){
                response.getWriter().write("{\"mensaje\":\"Publicacion insertada correctamente\"}");
            }else {
                response.getWriter().write("{\"error\":\"Error de insercion de la publicacion\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_publication = request.getParameter("id_publication");
        String id_user = request.getParameter("id_user");
        String enablePublication = request.getParameter("enable");
       
        if(id_publication != null && id_user != null){
            /**Insert like in the table and update like variable in the publication's table using trigger*/
            boolean updateOk = likeService.insertLike(id_user, Integer.parseInt(id_publication));
            if(updateOk){
                response.getWriter().write("{\"mensaje\":\"Actualizacion de Likes en publicacion correcta\"}");
            }else {
                response.getWriter().write(" {\"error\":\"Error al actualizar los Likes de la publicacion\"} ");
            }
        }   
        if(id_publication != null && enablePublication != null){
            boolean disableOk = publicationService.enableDisablePublication(Integer.parseInt(id_publication), Boolean.parseBoolean(enablePublication));
            if(disableOk){
                response.getWriter().write(" {\"mensaje\":\"Publicacion habilitada-deshabiltada correctamente\"}");
            }else{
                response.getWriter().write(" {\"error\":\"Error de habilitacion-deshabilitacion de la publicacion\"} ");
            }
       }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_publication = request.getParameter("id_publication");
        
        boolean deleteOk = publicationService.deletePublication(Integer.parseInt(id_publication));
        if(deleteOk){
            response.getWriter().write("{\"mensaje\":\"Publicacion eliminada correctamente\"}");
        }else {
            response.getWriter().write("{\"error\":\"Error al eliminar la publicacion\"}");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Publication Servlet";
    }// </editor-fold>
}
