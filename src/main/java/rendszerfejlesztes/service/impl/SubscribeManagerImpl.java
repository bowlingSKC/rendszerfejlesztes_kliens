package rendszerfejlesztes.service.impl;

import rendszerfejlesztes.Main;
import rendszerfejlesztes.modell.Event;
import rendszerfejlesztes.modell.Subscription;
import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.service.SubscribeManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.List;


public class SubscribeManagerImpl extends BaseManager implements SubscribeManager {

    @Override
    public Subscription subscribe(Event event){
        //MultivaluedMap<Event,User> params = new MultivaluedHashMap<Event,User>();
        //params.add(event, Main.getLoggedUser());


        WebTarget webTarget = getClient().target(getBaseTargetUrl()).path("subscribe").path("subscribe").path(event.getId().toString());

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Subscription sub = new Subscription();
        sub.setEvent(event);
        sub.setUser(Main.getLoggedUser());
        Response response = invocationBuilder.post(Entity.entity(Main.getLoggedUser(), MediaType.APPLICATION_JSON_TYPE));
        Subscription subscription = response.readEntity(Subscription.class);

        return subscription;

    }

    @Override
    public List<Subscription> getUserSubscription(User user){
        Response response = getClient().target( getBaseTargetUrl() ).path("subscribe").path("getSub").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        GenericType<List<Subscription>> wrapper = new GenericType<List<Subscription>>() {};
        List<Subscription> subscriptions = response.readEntity(wrapper);
        return subscriptions;
    }

    @Override
    public boolean unSubscribe(Subscription subscription){
        subscription.setUser(Main.getLoggedUser());
        WebTarget webTarget = getClient().target(getBaseTargetUrl()).path("subscribe").path("unsubscribe");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(subscription, MediaType.APPLICATION_JSON));
        if( response.getStatus() != 200 ) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSubscribed(Event event, User user){
        WebTarget webTarget = getClient().target(getBaseTargetUrl()).path("subscribe").path("issubscribed").path(user.getId().toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(event, MediaType.APPLICATION_JSON));
        if( response.getStatus() != 200 ) {
            return false;
        }
        return true;
    }
}
