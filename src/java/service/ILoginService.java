package service;

import model.dto.UserDTO;

public interface ILoginService {
    public UserDTO login(String id_usuario, String password);
}
