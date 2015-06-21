package service.instances;

import model.dao.WallDAO;
import model.dto.WallDTO;

public class WallService {
    private WallDAO wallDAO;

    public WallService() {
        wallDAO = new WallDAO();
    }
    
    public WallDTO getMuro(short id_career, short id_subject) {
        return wallDAO.getMuro(id_career, id_subject);
    }
    
    public boolean enableDisabledMuro(short id_career, short id_subject, boolean enable){
        return wallDAO.enableDisableMuro(id_career, id_subject, enable);
    }
}
