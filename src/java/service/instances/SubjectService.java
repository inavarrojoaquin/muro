package service.instances;

import java.util.List;
import model.dao.SubjectDAO;
import model.dts.SubjectDTO;
import model.interfaces.IAbstractFactoryModel;
import service.AbstractMuroService;

public class SubjectService extends AbstractMuroService<SubjectDTO, String>{
    private IAbstractFactoryModel subjectDAO;

    public SubjectService() {
        subjectDAO = new SubjectDAO();
    }
    
    @Override
    public List<SubjectDTO> get(List<String> object) {
        return subjectDAO.selectAll(object);
    }
    
    @Override
    public List<SubjectDTO> get(String object) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
