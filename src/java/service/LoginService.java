package service;

import model.dao.UserDAO;
import model.dts.UserDTO;
import model.interfaces.IAbstractFactoryModel;

/**
 *
 * @author Febo
 */
public class LoginService implements ILoginService{
    private IAbstractFactoryModel userDAO;
    private UserDTO userDTO;
    
    public LoginService() {
        userDAO = new UserDAO();
    }
    
    public LoginService(IAbstractFactoryModel userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO login(String id_usuario, String password) {
        userDTO = new UserDTO(id_usuario, password);
        return (UserDTO) userDAO.select(userDTO);
    }
}
