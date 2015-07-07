package service.instances;

import java.util.List;
import model.dao.SubjectDAO;
import model.dto.SubjectDTO;

public class SubjectService {
    private SubjectDAO subjectDAO;

    public SubjectService() {
        subjectDAO = new SubjectDAO();
    }
    
    public List<SubjectDTO> getSubjects(String id_user, short id_career) {
        return subjectDAO.selectAllSubjects(id_user, id_career);
    }
    
    public boolean enableDisabledMuro(short id_career, short id_subject, boolean enable){
        return subjectDAO.enableDisableMuro(id_career, id_subject, enable);
    }
}
