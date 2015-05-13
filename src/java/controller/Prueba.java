/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;
import java.util.List;
import model.dao.UserDAO;
import model.dts.UserDTO;
import service.ILoginService;
import service.LoginService;

/**
 *
 * @author Febo
 */
public class Prueba {
    public static void main(String[] args){
        UserDAO user = new UserDAO();
        UserDTO userdto = new UserDTO("555", "555");
        
        ILoginService login = new LoginService();
        UserDTO u = login.login("555", "555");
        if(u != null){
            System.out.println("nombre"+u.getNombre());
            System.out.println("apellido"+u.getApellido());
        }else {
            System.out.println("ERROR");
        }
        
        
        
//        LoginController login;
//        login = new LoginController();
//        
        
    }
}
