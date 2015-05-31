package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dts.SubjectDTO;
import model.dts.SubjectDTO;
import service.AbstractMuroService;
import service.instances.SubjectService;

@WebServlet(name = "SubjectServlet", urlPatterns = {"/subject.do"})
public class SubjectServlet extends HttpServlet {
    AbstractMuroService subjectService;
    HttpSession session;

    public SubjectServlet() {
        subjectService = new SubjectService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        String id_usuario = (String)session.getAttribute("id_user");
        String nombre = (String)request.getParameter("name");
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        Type listType = new TypeToken<List<SubjectDTO>>(){}.getType();        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        String representacionJSON = "";
        List<String> list = new ArrayList();
        list.add(id_usuario);
        list.add(nombre);
        
        List<SubjectDTO> subjectList = subjectService.get(list);
        if (subjectList != null) {
            representacionJSON = gson.toJson(subjectList, listType);   
        }
        response.getWriter().write(gson.toJson(representacionJSON));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Subject Servlet";
    }// </editor-fold>

}
