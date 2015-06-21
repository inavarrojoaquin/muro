package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dto.SubjectDTO;
import service.instances.SubjectService;

@WebServlet(name = "SubjectServlet", urlPatterns = {"/subject.do"})
public class SubjectServlet extends HttpServlet {
    private SubjectService subjectService;
    HttpSession session;

    public SubjectServlet() {
        subjectService = new SubjectService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        session = request.getSession();
        String id_user = (String)session.getAttribute("id_user");
        String id_career = request.getParameter("id_career");
        
        if(id_user != null && id_career != null){
            Type listType = new TypeToken<List<SubjectDTO>>(){}.getType();        
            Gson gson = new GsonBuilder().serializeNulls().create();

            String representacionJSON;
            List<SubjectDTO> subjectList = subjectService.getSubjects(id_user, Short.parseShort(id_career));
            if (subjectList != null) {
                representacionJSON = gson.toJson(subjectList, listType);   
            }else {
                representacionJSON = " {\"error\":\"No hay materias para mostrar\" } ";
            }
            response.getWriter().write(representacionJSON);
        }
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
