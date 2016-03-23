package rendszerfejlesztes.service.impl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BaseManager {

    private static Client client = ClientBuilder.newClient(new ClientConfig().register( JacksonFeature.class ));
    private static final String BASE_TARGET_URL = "http://localhost:8080/szerver_Web_exploded/api";

    public static Client getClient() {
        return client;
    }

    public static String getBaseTargetUrl() {
        return BASE_TARGET_URL;
    }
}
