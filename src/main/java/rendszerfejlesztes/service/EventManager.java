package rendszerfejlesztes.service;

import rendszerfejlesztes.modell.Event;
import rendszerfejlesztes.modell.Location;
import rendszerfejlesztes.modell.Sector;

import java.util.List;

public interface EventManager {

    List<Event> getAllEvent();
    List<Event> searchEvent(Event conds);

    List<Location> getAllLocations();
    Event getEventBySector(Sector sector);

}
