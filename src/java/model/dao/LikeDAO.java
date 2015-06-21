package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.LikeDTO;

public class LikeDAO {
    
    private static final String SQL_SELECT_USER_LIKE = "{ call proc_getUserLike(?) }";//id_usuario - retorna lista de id_publicacion
    private static final String SQL_SELECT_ALLUSERSBYPUBLICATION_LIKES = "{ call proc_getAllUsersByPublication_Like(?) }";//id_publicacion - retorta id_usuario, nombre, apellido
    private static final String SQL_INSERT_LIKE = "{ call proc_insertLike(?, ?) }";//id_usuario, id_publicacion - inserta un like
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private LikeDTO like;
    private List<LikeDTO> list;
    
    public List<LikeDTO> getUserLike(String id_user) {
        try {
            like = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_USER_LIKE);
            ps.setString(1, id_user);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                like = new LikeDTO(rs.getInt("id_publicacion"));
                list.add(like);
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
    
    public List<LikeDTO> getAllUsersByPublicationLike(int id_publication) {
        try {
            like = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_ALLUSERSBYPUBLICATION_LIKES);
            ps.setObject(1, id_publication);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                like = new LikeDTO(rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("apellido"));
                list.add(like);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CareerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(CareerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public boolean insertLike(String id_user, int id_publication) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_INSERT_LIKE);
            ps.setString(1, id_user);
            ps.setInt(2, id_publication);
            
            if(ps.executeUpdate() > 0){
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
}
