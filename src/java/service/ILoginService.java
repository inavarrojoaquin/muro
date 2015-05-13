package service;

import model.dts.UserDTO;

/**
 *
 * @author Febo
 */
public interface ILoginService {
    public UserDTO login(String id_usuario, String password);
}
