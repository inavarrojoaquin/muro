package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.CareerDTO;

public class CareerDAO {

    private static final String SQL_SELECT_CAREERS = "{ call proc_getCarreraPorUsuario(?) }"; //id_usuario. return career's name
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private CareerDTO career;
    private List<CareerDTO> list;

    public List<CareerDTO> getCareersByIdUser(String id_user) {
        try {
            career = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_CAREERS);
            ps.setString(1, id_user);
            
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
}
