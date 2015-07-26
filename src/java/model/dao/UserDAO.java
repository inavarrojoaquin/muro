package model.dao;

import model.connection.ConnectDB;
import model.dto.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final String SQL_UPDATE_DATE = "{ call proc_updateAccesoUsuario_fecha_acceso(?, ?) }"; //id_usuario, fecha
    private static final String SQL_SELECT_USER = "{ call proc_AccesoUsuario(?, ?) }"; //id_usuario, password
    private static final String SQL_SELECT_USERS = "{ call proc_selectAllUsers }"; //return Users list
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private UserDTO user;

    public UserDTO getUser(String id_user, String password) {
        try {
            user = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_USER);
            ps.setString(1, id_user);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            while(rs.next()){
                user = new UserDTO(rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("apellido"), rs.getShort("id_rol"), rs.getString("fecha_acceso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }
    
    public boolean updateDate(String id_user) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String time = format.format(date);
        
        try {
            ps = conn.getConnection().prepareStatement(SQL_UPDATE_DATE);
            ps.setString(1, id_user);
            ps.setString(2, time);
            
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public List<UserDTO> getAllUsers() {
        ArrayList<UserDTO> list = null;

        try {
            ps = conn.getConnection().prepareStatement(SQL_SELECT_USERS);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                UserDTO user = new UserDTO(rs.getString("id_usuario"), rs.getString("nombre"));
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }  
}
