package service.instances;

import java.util.ArrayList;
import java.util.List;
import model.dao.WallDAO;
import model.dto.WallDTO;
import model.interfaces.IAbstractFactoryModel;
import service.AbstractMuroService;

public class WallService extends AbstractMuroService<WallDTO, String>{
    private IAbstractFactoryModel wallDAO;

    public WallService() {
        wallDAO = new WallDAO();
    }
    
    @Override
    public List<WallDTO> get(List<String> object) {
        List<String> list = new ArrayList();
        list.add(object.get(0));
        list.add(object.get(1));
        return wallDAO.selectAll(list);
    }
    
    @Override
    public List<WallDTO> get(String object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
