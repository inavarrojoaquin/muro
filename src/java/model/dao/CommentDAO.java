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

public class CommentDAO {
    private static final String SQL_SELECT_COMMENTS_BY_PUBLICATION = "{ call proc_getAllCommentsByPublication(?) }"; //id_publicacion, return all comments
    private static final String SQL_INSERT_COMMENT = "{ call proc_insertCommentInPublication(?, ?, ?) }"; //texto,id_usuario,id_publicacion
    private static final String SQL_DELETE_COMMENT = "{ call proc_deleteComment(?) }"; //id_comentario, set 'eliminado=1'
            
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private CommentDTO comment;
    private List<CommentDTO> list;
    
    public List<CommentDTO> selectAllComments(int id_publication) {
        try {
            comment = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_COMMENTS_BY_PUBLICATION);
            ps.setInt(1, id_publication);
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                comment = new CommentDTO(rs.getInt("id_comentario"), rs.getString("texto"), rs.getInt("id_publicacion"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("fecha_creacion"));
                list.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public boolean insertComment(String texto, String id_user, int id_publication) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_INSERT_COMMENT);
            ps.setString(1, texto);
            ps.setString(2, id_user);
            ps.setInt(3, id_publication);
            
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
    
    public boolean deleteComment(int id_comment) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_DELETE_COMMENT);
            ps.setInt(1, id_comment);
            
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
