package rendszerfejlesztes.service.impl;

import rendszerfejlesztes.Main;
import rendszerfejlesztes.modell.*;
import rendszerfejlesztes.service.EventManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class EventManagerImpl extends BaseManager implements EventManager {

    public List<Event> getAllEvent() {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("event");
        GenericType<List<Event>> wrapper = new GenericType<List<Event>>() {};
        List<Event> events = webTarget.request().get(wrapper);
        return events;
    }

    public List<Event> searchEvent(Event conds) {
        GenericType<List<Event>> wrapper = new GenericType<List<Event>>() {};
        Response post = getClient().target(getBaseTargetUrl()).path("event").path("search").request().post(Entity.entity(conds, MediaType.APPLICATION_JSON));

        List<Event> events = post.readEntity(wrapper);
        return events;
    }

    @Override
    public List<Location> getAllLocations() {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("event").path("locations");
        GenericType<List<Location>> wrapper = new GenericType<List<Location>>() {};
        List<Location> locations = webTarget.request().get(wrapper);
        return locations;
    }

    @Override
    public Event getEventBySector(Sector sector) {
        WebTarget webTarget = getClient().target( getBaseTargetUrl() ).path("event").path("sectors").path("bysector");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(sector, MediaType.APPLICATION_JSON));
        Event event = response.readEntity(Event.class);

        return event;
    }

    @Override
    public Event getEventBySubscription(Subscription subscription){
        WebTarget webTarget  = getClient().target(getBaseTargetUrl()).path("event").path("bysubscription").path(subscription.getId().toString());

        //Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = webTarget.request().get();
        Event event = response.readEntity(Event.class);

        return event;
    }
}
