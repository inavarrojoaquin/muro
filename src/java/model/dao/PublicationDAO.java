package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.CommentDTO;
import model.dto.PublicationDTO;
import service.instances.CommentService;

public class PublicationDAO {
    private static final String SQL_SELECT_PUBLICATIONS = "{ call proc_getPublicacionesMuro(?) }";//id_muro - retorna id_publicacion,texto,likes,fecha_publicacion
    private static final String SQL_SELECT_PUBLICATIONS_BEFORE_DATE = "{ call proc_getPublicacionesMuroAfterDate(?, ?) }"; //id_muro, fecha - retorna las publicaciones posteriores a esa fecha
    private static final String SQL_INSERT_PUBLICATION = "{ call proc_insertPublicacion(?, ?, ?) }";//texto, id_muro, id_usuario - inserta publicacion
    private static final String SQL_DELETE_PUBLICATION = "{ call proc_deletePublicacion(?) }"; //id_publicacion - setea 'eliminado=1'
    private static final String SQL_UPDATE_PUBLICATION_ENABLE_DISABLE = "{ call proc_updatePublicacion_EnableDisable(?, ?)}"; //id_publicacion, habilitado(boolean) - setea 'habilitado'
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private PublicationDTO publication;
    private List<PublicationDTO> list;
    private final CommentService commentService = new CommentService();
    private List<CommentDTO> commentList;
    
    public List<PublicationDTO> getPublications(short id_wall) {
        try {
            publication = null;
            list = null;
            commentList = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_PUBLICATIONS);
            ps.setShort(1, id_wall);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                    commentList = new ArrayList();
                }
                commentList = commentService.getCommentsByPublication(rs.getInt("id_publicacion"));
                publication = new PublicationDTO(rs.getInt("id_publicacion"), rs.getString("texto"), rs.getShort("likes"), "Me gusta", rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("fecha_publicacion"), commentList);
                list.add(publication);
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
    
    public List<PublicationDTO> getPublicationsBeforeDate(short id_wall, String date) {
        try {
            publication = null;
            list = null;
            commentList = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_PUBLICATIONS_BEFORE_DATE);
            ps.setShort(1, id_wall);
            ps.setString(2, date);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                    commentList = new ArrayList();
                }
                commentList = commentService.getCommentsByPublication(rs.getInt("id_publicacion"));
                publication = new PublicationDTO(rs.getInt("id_publicacion"), rs.getString("texto"), rs.getShort("likes"), "Me gusta", rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("fecha_publicacion"), commentList);
                list.add(publication);
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
    
    public boolean insertPublication(String texto, short id_wall, String id_user) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_INSERT_PUBLICATION);
            ps.setString(1, texto);
            ps.setShort(2, id_wall);
            ps.setString(3, id_user);
            
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

    public boolean deletePublication(int id_publication) {
        try {//id_publicacion - setea 'eliminado=1'
            ps = conn.getConnection().prepareStatement(SQL_DELETE_PUBLICATION);
            ps.setInt(1, id_publication);
            
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
    
    //Habilita-deshabilita una publicacion
    public boolean enableDisablePublication(int id_publication, boolean enable) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_UPDATE_PUBLICATION_ENABLE_DISABLE);
            ps.setInt(1, id_publication);
            ps.setBoolean(2, enable);
            
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
