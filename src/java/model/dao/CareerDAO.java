package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dts.CareerDTO;
import model.interfaces.IAbstractFactoryModel;

public class CareerDAO implements IAbstractFactoryModel<CareerDTO, String>{

    private static final String SQL_SELECTALL = "{ call proc_getCarreraPorUsuario(?) }"; //id_usuario. return career's name
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private CareerDTO career;
    private List<CareerDTO> list;

    @Override
    public List<CareerDTO> selectAll(List<String> a) {
        try {
            career = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECTALL);
            ps.setObject(1, a.get(0));
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                career = new CareerDTO(rs.getShort("id_carrera"), rs.getString("nombre"));
                list.add(career);
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
    public CareerDTO select(CareerDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean insert(CareerDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(CareerDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CareerDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
