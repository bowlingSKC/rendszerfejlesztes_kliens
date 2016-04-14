package rendszerfejlesztes.service;

import rendszerfejlesztes.modell.*;

import java.util.List;

public interface EventManager {

    List<Event> getAllEvent();
    List<Event> searchEvent(Event conds);

    List<Location> getAllLocations();
    Event getEventBySector(Sector sector);
    Event getEventBySubscription(Subscription subscription);

    List<Performer> getAllPerformer();
    Performer createNewPerformer(Performer performer);

    Event createNewEvent(Event newEvent);
}
