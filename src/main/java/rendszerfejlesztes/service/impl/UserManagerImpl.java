package rendszerfejlesztes.service.impl;

import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.modell.exceptions.UserAlreadyExistsException;
import rendszerfejlesztes.service.UserManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserManagerImpl extends BaseManager implements UserManager {

    public User create(User user) throws UserAlreadyExistsException {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("user");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(user, MediaType.APPLICATION_JSON));
        User created = response.readEntity(User.class);

        return created;
    }

    public User login(String email, String pswd) {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("user").path(email).path(pswd);
        User created = webTarget.request().get(User.class);
        return created;
    }

    public List<User> getAllUser() {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("user");
        GenericType<List<User>> wrapper = new GenericType<List<User>>() {};
        List<User> users = webTarget.request().get(wrapper);
        return users;
    }

}
