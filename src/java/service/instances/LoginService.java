package service.instances;

import model.dao.UserDAO;
import model.dto.UserDTO;
import service.ILoginService;

public class LoginService implements ILoginService{
    private UserDAO userDAO;
    
    public LoginService() {
        userDAO = new UserDAO();
    }

    @Override
    public UserDTO login(String id_user, String password) {
        return (UserDTO) userDAO.getUser(id_user, password);
    }

    @Override
    public boolean updateDate(String id_user) {
        return userDAO.updateDate(id_user);
    }
}
