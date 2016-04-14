package rendszerfejlesztes.service;


import rendszerfejlesztes.controllers.SubscribeController;
import rendszerfejlesztes.modell.Event;
import rendszerfejlesztes.modell.Subscription;
import rendszerfejlesztes.modell.User;

import javax.naming.InsufficientResourcesException;
import java.util.List;

public interface SubscribeManager {
    Subscription subscribe(Event event);
    boolean unSubscribe(Subscription subscription);
    List<Subscription> getUserSubscription(User user);
    boolean isSubscribed(Event event, User user);
}
