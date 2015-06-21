package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.connection.ConnectDB;
import model.dto.SubjectDTO;

public class SubjectDAO {

    private static final String SQL_SELECT_SUBJECTS = "{ call proc_getMateriaCarreraUsuario(?, ?) }"; //id_usuario, id_career. return materia's name
    
    private static final ConnectDB conn = ConnectDB.getInstance(); //use singleton
    private PreparedStatement ps;
    private ResultSet rs;
    private SubjectDTO subject;
    private List<SubjectDTO> list;
    
    public List<SubjectDTO> selectAllSubjects(String id_user, short id_career) {
        try {
            subject = null;
            list = null;
            ps = conn.getConnection().prepareStatement(SQL_SELECT_SUBJECTS);
            ps.setString(1, id_user);
            ps.setShort(2, id_career);
            
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
}
