package rendszerfejlesztes.service.impl;

import rendszerfejlesztes.service.TestConnection;

import javax.ws.rs.client.WebTarget;

public class TestConnectionImpl extends BaseManager implements TestConnection {

    @Override
    public void ping() {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("test");
        String response = webTarget.request().get().readEntity(String.class);
        if( !response.equals("OK") ) {
            throw new RuntimeException("Nem lehet felvenni a kapcsolatot a szerverrel!");
        }
    }

}
