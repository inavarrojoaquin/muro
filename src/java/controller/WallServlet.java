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

    public WallServlet() {
        wallService = new WallService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        
        List<WallDTO> wallList = wallService.get(list);
        if (wallList != null) {
            representacionJSON = gson.toJson(wallList, listType);   
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
