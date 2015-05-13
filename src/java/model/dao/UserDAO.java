package model.dao;


import model.connection.ConnectDB;
import model.dts.UserDTO;
import model.interfaces.IAbstractFactoryModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Febo
 */
public class UserDAO implements IAbstractFactoryModel<UserDTO, String> {
    private static final String SQL_UPDATE = "{ call proc_updateAccesoUsuario_fecha_acceso(?, ?, ?) }"; //id_usuario, password, fecha
    private static final String SQL_SELECT = "{ call proc_AccesoUsuario(?, ?) }"; //id_usuario, password
    private static final String SQL_SELECTALL = "SELECT * FROM AccesoUsuario";
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private UserDTO user = null;

    @Override
    public UserDTO select(UserDTO a) {
        try {
            ps = conn.getConnection().prepareStatement(SQL_SELECT);
            ps.setString(1, a.getIdUsuario());
            ps.setString(2, a.getPassword());
            
            rs = ps.executeQuery();
            while(rs.next()){
                user = new UserDTO(rs.getString("id_usuario"), rs.getString("nombre"), rs.getString("apellido"));
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
    
    @Override
    public boolean update(UserDTO a) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        String time = format.format(date);
        
        try {
            ps = conn.getConnection().prepareStatement(SQL_UPDATE);
            ps.setString(1, a.getIdUsuario());
            ps.setString(2, a.getPassword());
            ps.setString(3, time);
            
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

    @Override
    public List<UserDTO> selectAll() {
        ArrayList<UserDTO> list = new ArrayList();

        try {
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(SQL_SELECTALL);
            while(rs.next()){
                UserDTO user = new UserDTO(rs.getString("id_usuario"));
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
    
    /*Not implemented*/
    @Override
    public boolean insert(UserDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
