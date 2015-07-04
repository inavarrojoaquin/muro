package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import model.dao.CareerDAO;
import model.dao.UserDAO;
import model.dto.CareerDTO;
import model.dto.CommentDTO;
import model.dto.PublicationDTO;
import model.dto.SubjectDTO;
import model.dto.UserDTO;
import model.dto.WallDTO;
import service.instances.CareerService;
import service.ILoginService;
import service.AbstractMuroService;
import service.instances.CommentService;
import service.instances.LoginService;
import service.instances.PublicationService;
import service.instances.SubjectService;
import service.instances.WallService;

/**
 *
 * @author Febo
 */
public class Prueba {
    public static void main(String[] args){
        
//        ILoginService login = new LoginService();
//        UserDTO u = login.login("555", "555");
//        boolean updateOK = login.updateDate("555");
//        
//        if(u != null && updateOK == true){
//            System.out.println("id_usuario"+u.getIdUsuario());
//            System.out.println("nombre"+u.getNombre());
//            System.out.println("apellido"+u.getApellido());
//            System.out.println("id_rol"+u.getIdRol());
//        }else {
//            System.out.println("ERROR");
//            System.out.println("User null");
//        }
        
//        CareerService career = new CareerService();
//        List<CareerDTO> list = career.getCareersByIdUser("555");
//        for (Iterator<CareerDTO> iterator = list.iterator(); iterator.hasNext();) {
//            CareerDTO next = iterator.next();
//            System.out.println("id_carrera: " + next.getId_carrera());
//            System.out.println("NombreCarrera: " + next.getNombre());
//        }
        
//        AbstractMuroService subject = new SubjectService();
//        List<String> lista = new ArrayList();
//        lista.add("555");
//        lista.add("INGENIERIA INFORMATICA");
//        
//        List<SubjectDTO> listSubject = subject.get(lista);
//        for (Iterator<SubjectDTO> iterator = listSubject.iterator(); iterator.hasNext();) {
//            SubjectDTO next = iterator.next();
//            System.out.println("IdMateria: " + next.getId_materia());
//            System.out.println("NombreMateria: " + next.getNombre());
//
//        }
        
//        AbstractMuroService wall = new WallService();
//        List<String> lista = new ArrayList();
//        lista.add("1");
//        lista.add("1");
//        
//        List<WallDTO> listWall = wall.get(lista);
//        for (Iterator<WallDTO> iterator = listWall.iterator(); iterator.hasNext();) {
//            WallDTO next = iterator.next();
//            System.out.println("Id_muro: " + next.getId_muro());
//            System.out.println("Fecha_creacion: " + next.getFecha_creacion());
//
//        }
//        AbstractMuroService publication = new PublicationService();
//        //Yo deberia saber que parametros debo pasarle al metodo.
//        PublicationDTO publicationDTO = new PublicationDTO("Comentario de prueba CONSOLA", Short.parseShort("0"), "555");
//        
//        if(publication.insert(publicationDTO)){
//            System.out.println("La publicacion de inserto correctamente");
//        }
        
//        PublicationService publicationService = new PublicationService();
//        boolean ok = publicationService.enableDisablePublication(1003, false);
//        if(ok){
//            System.out.println("Ok");
//        }else{
//            System.out.println("error");
//        }
        
        CommentService commentService = new CommentService();
        List<CommentDTO> commentList = commentService.getCommentsByPublication(2);
        if(commentList != null){
            for(CommentDTO comment : commentList){
                System.out.println("Datos: "+ comment.getTexto());
            }
            
        }else{
            System.out.println("No hay datos...");
        }
               
    }
}
