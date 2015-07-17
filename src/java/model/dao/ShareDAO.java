package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;

public class ShareDAO {
    private static final String SQL_INSERT_SHARE_PUBLICATION = "{ call proc_insertSharePublication(?, ?, ? ,?) }";//id_publicacion, id_usuario_comparte, texto, destino(F-N)
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    
    public boolean insertShare(int id_publication, String id_user_share, String text, String destination) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_INSERT_SHARE_PUBLICATION);
            ps.setInt(1, id_publication);
            ps.setString(2, id_user_share);
            ps.setString(3, text);
            ps.setString(4, destination);
            
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
