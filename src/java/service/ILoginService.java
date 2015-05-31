package service;

import model.dts.UserDTO;

public interface ILoginService {
    public UserDTO login(String id_usuario, String password);
}
