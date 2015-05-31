package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import model.dao.CareerDAO;
import model.dao.UserDAO;
import model.dts.CareerDTO;
import model.dts.SubjectDTO;
import model.dts.UserDTO;
import service.instances.CareerService;
import service.ILoginService;
import service.AbstractMuroService;
import service.instances.LoginService;
import service.instances.SubjectService;

/**
 *
 * @author Febo
 */
public class Prueba {
    public static void main(String[] args){
//        UserDAO user = new UserDAO();
//        UserDTO userdto = new UserDTO("555", "444");
//        
//        ILoginService login = new LoginService();
//        UserDTO u = login.login("555", "444");
//        if(u != null){
//            System.out.println("nombre"+u.getNombre());
//            System.out.println("apellido"+u.getApellido());
//        }else {
//            System.out.println("ERROR");
//            System.out.println("User null");
//        }
//        
//        AbstractMuroService career = new CareerService();
//        List<CareerDTO> list = career.get("666");
//        for (Iterator<CareerDTO> iterator = list.iterator(); iterator.hasNext();) {
//            CareerDTO next = iterator.next();
//            System.out.println("NombreCarrera: " + next.getId_carrera());
//            System.out.println("NombreCarrera: " + next.getNombre());
//
//        }
        
        AbstractMuroService subject = new SubjectService();
        List<String> lista = new ArrayList();
        lista.add("555");
        lista.add("INGENIERIA INFORMATICA");
        
        List<SubjectDTO> listSubject = subject.get(lista);
        for (Iterator<SubjectDTO> iterator = listSubject.iterator(); iterator.hasNext();) {
            SubjectDTO next = iterator.next();
            System.out.println("IdMateria: " + next.getId_materia());
            System.out.println("NombreMateria: " + next.getNombre());

        }
               
    }
}
