package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dts.SubjectDTO;
import model.interfaces.IAbstractFactoryModel;

public class SubjectDAO implements IAbstractFactoryModel<SubjectDTO, String>{

    private static final String SQL_SELECTALL = "{ call proc_getMateriaCarreraUsuario(?, ?) }"; //id_usuario, carrera. return materia's name
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private SubjectDTO subject;
    private List<SubjectDTO> list;
    
    @Override
    public List<SubjectDTO> selectAll(List<String> a) {
        try {
            subject = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECTALL);
            ps.setObject(1, a.get(0));
            ps.setObject(2, a.get(1));
            
            rs = ps.executeQuery();
            while(rs.next()){
                if(list == null){
                    list = new ArrayList();
                }
                subject = new SubjectDTO(rs.getShort("id_materia"), rs.getString("nombre"));
                list.add(subject);
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
    
    @Override
    public boolean insert(SubjectDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(SubjectDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubjectDTO select(SubjectDTO a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubjectDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
