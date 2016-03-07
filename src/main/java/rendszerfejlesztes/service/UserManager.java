package rendszerfejlesztes.service;

import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.modell.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface UserManager {

    User create(User user) throws UserAlreadyExistsException;
    User login(String email, String pswd);
    List<User> getAllUser();

}
