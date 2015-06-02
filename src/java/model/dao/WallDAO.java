package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.WallDTO;
import model.interfaces.IAbstractFactoryModel;

public class WallDAO implements IAbstractFactoryModel<WallDTO, String>{

    private static final String SQL_SELECTALL = "{ call proc_getMuro(?, ?) }"; //id_carrera, id_materia. return id_muro
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private WallDTO wall;
    private List<WallDTO> list;
    
    @Override
    public List<WallDTO> selectAll(List<String> a) {
        try {
            wall = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECTALL);
            ps.setObject(1, a.get(0));
            ps.setObject(2, a.get(1));
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                wall = new WallDTO(rs.getShort("id_muro"), rs.getString("fecha_creacion"));
                list.add(wall);
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
    
    @Override
    public WallDTO select(WallDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.   
    }
    
    @Override
    public boolean insert(WallDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(WallDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WallDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
