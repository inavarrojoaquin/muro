package service.instances;

import java.util.ArrayList;
import java.util.List;
import model.dao.CareerDAO;
import model.dto.CareerDTO;
import model.interfaces.IAbstractFactoryModel;
import service.AbstractMuroService;

public class CareerService extends AbstractMuroService<CareerDTO, String>{
    private IAbstractFactoryModel careerDAO;

    public CareerService() {
        careerDAO = new CareerDAO();
    }
    
    @Override
    public List<CareerDTO> get(String id_usuario) {
        List<String> list = new ArrayList();
        list.add(id_usuario);
        return careerDAO.selectAll(list);
    }

    @Override
    public List<CareerDTO> get(List<String> object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
