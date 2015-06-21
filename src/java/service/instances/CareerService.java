package service.instances;

import java.util.List;
import model.dao.CareerDAO;
import model.dto.CareerDTO;

public class CareerService {
    private CareerDAO careerDAO;

    public CareerService() {
        careerDAO = new CareerDAO();
    }
    
    public List<CareerDTO> getCareersByIdUser(String id_usuario) {
        return careerDAO.getCareersByIdUser(id_usuario);
    } 
}
