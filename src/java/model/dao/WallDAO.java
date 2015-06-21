package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.WallDTO;

public class WallDAO{

    private static final String SQL_SELECT_MURO = "{ call proc_getMuro(?, ?) }"; //id_carrera, id_materia. return id_muro
    private static final String SQL_UPDATE_MURO_ENABLE_DISABLE = "{ call proc_updateMuro_EnableDisable(?, ?, ?) }"; //id_carrera, id_materia, habilitado(boolean). update 'habilitado'
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private WallDTO wall;
    
    public WallDTO getMuro(short id_career, short id_muro) {
        try {
            wall = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_MURO);
            ps.setShort(1, id_career);
            ps.setShort(2, id_muro);
            
            rs = ps.executeQuery();
            while(rs.next()){
                wall = new WallDTO(rs.getShort("id_muro"), rs.getString("fecha_creacion"));
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
        return wall;
    }
    
    public boolean enableDisableMuro(short id_career, short id_subject, boolean enable) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_UPDATE_MURO_ENABLE_DISABLE);
            ps.setShort(1, id_career);
            ps.setShort(2, id_subject);
            ps.setBoolean(3, enable);
            
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
}
