package rendszerfejlesztes.service.impl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.service.UserManager;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserManagerImpl implements UserManager {

    public void create(User user) {
        Client client = ClientBuilder.newClient(new ClientConfig().register( JacksonFeature.class ));
        WebTarget webTarget = client.target("http://localhost:8080/veszpremfeszt/api").path("myresource");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

        User created = response.readEntity(User.class);
        System.out.println("Created on server: " + created);
    }

}
