package rendszerfejlesztes.service;


import rendszerfejlesztes.controllers.SubscribeController;
import rendszerfejlesztes.modell.Event;
import rendszerfejlesztes.modell.Subscription;
import rendszerfejlesztes.modell.User;

import java.util.List;

public interface SubscribeManager {
    Subscription subscribe(Event event);
    List<Subscription> getUserSubscription(User user);
}
