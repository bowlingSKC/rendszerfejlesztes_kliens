package rendszerfejlesztes.service.impl;

import rendszerfejlesztes.modell.Sector;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.service.TicketManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class TicketManagerImpl extends BaseManager implements TicketManager {

    @Override
    public List<Ticket> getTicketByUser(User user) {
        Response response = getClient().target( getBaseTargetUrl() ).path("booking").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        GenericType<List<Ticket>> wrapper = new GenericType<List<Ticket>>() {};
        List<Ticket> events = response.readEntity(wrapper);
        return events;
    }

    @Override
    public Sector getSectorByTicket(Ticket ticket) {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("booking").path("sectors");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(ticket, MediaType.APPLICATION_JSON));
        Sector sector = response.readEntity(Sector.class);
        return sector;
    }

    @Override
    public Ticket bookTicket(Ticket ticket) {

        Response response = getClient().target( getBaseTargetUrl() ).path("booking").path("book").request().put(Entity.entity(ticket, MediaType.APPLICATION_JSON));
        if( response.getStatus() != 200 ) {
            throw new RuntimeException("Nem sikerult a mentes szerver oldalon!");
        }
        return response.readEntity(Ticket.class);
    }

    @Override
    public boolean removeTickets(Ticket ticket){
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("booking").path("delete");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(ticket, MediaType.APPLICATION_JSON));
        if( response.getStatus() == 400 ) {
            return false;
        }
        return true;
    }

    public Ticket setPaidTrue(Ticket ticket){
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("user").path("paid");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.put(Entity.entity(ticket, MediaType.APPLICATION_JSON));
        Ticket created = response.readEntity(Ticket.class);

        return created;
    }

}
