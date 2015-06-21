package service;

import model.dto.UserDTO;

public interface ILoginService {
    public UserDTO login(String id_user, String password);
    public boolean updateDate(String id_user);
}
