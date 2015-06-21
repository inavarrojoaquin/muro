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
import model.dto.CareerDTO;
import service.instances.CareerService;

@WebServlet(name = "CareerServlet", urlPatterns = {"/career.do"})
public class CareerServlet extends HttpServlet {
    private CareerService careerService;
    private HttpSession session;

    public CareerServlet() {
        this.careerService = new CareerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        session = request.getSession();
        String id_usuario = (String)session.getAttribute("id_user");
        
        if(id_usuario != null){
            Type listType = new TypeToken<List<CareerDTO>>(){}.getType();        
            Gson gson = new GsonBuilder().serializeNulls().create();

            String representacionJSON;
            List<CareerDTO> careerList = careerService.getCareersByIdUser(id_usuario);
            if (careerList != null) {
                representacionJSON = gson.toJson(careerList, listType);
            }else {
                representacionJSON = " {\"error\":\"No hay carreras para mostrar\"} ";
            }
            response.getWriter().write(representacionJSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

//    private void write(HttpServletResponse response, Object object) throws IOException {
//        String json = new Gson().toJson(object);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json");
//        response.getWriter().write(json); 
//    }
//    
    @Override
    public String getServletInfo() {
        return "Career Servlet";
    }// </editor-fold>
}
