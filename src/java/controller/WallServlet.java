package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dto.WallDTO;
import service.AbstractMuroService;
import service.instances.WallService;

@WebServlet(name = "WallServlet", urlPatterns = {"/wall.do"})
public class WallServlet extends HttpServlet {
    private WallService wallService;

    public WallServlet() {
        wallService = new WallService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String id_career = request.getParameter("id_career");
        String id_subject = request.getParameter("id_subject");
        String enable = request.getParameter("enable");
        String representacionJSON; 
        
        if(id_career != null && id_subject != null && enable == null){
            Type type = new TypeToken<WallDTO>(){}.getType();        
            Gson gson = new GsonBuilder().serializeNulls().create();
            
            WallDTO wall = wallService.getMuro(Short.parseShort(id_career), Short.parseShort(id_subject));
            if (wall != null) {
                representacionJSON = gson.toJson(wall, type);   
            }else {
                representacionJSON = " {\"error\":\"No se encontro el muro\" } ";
            }
            response.getWriter().write(representacionJSON);
        }else {
            boolean enableOk = wallService.enableDisabledMuro(Short.parseShort(id_career), Short.parseShort(id_subject), Boolean.parseBoolean(enable));
            if (enableOk) {
                representacionJSON = " {\"mensaje\":\"Muro habiltado-deshabilitado correctamente\"} ";
            }else {
                representacionJSON = " {\"error\":\"Error, no se pudo habilitar-deshabilitar el muro\" } ";
            }
            response.getWriter().write(representacionJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    @Override
    public String getServletInfo() {
        return "Wall Servlet";
    }// </editor-fold>
}
