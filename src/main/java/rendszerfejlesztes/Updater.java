package rendszerfejlesztes;

import rendszerfejlesztes.modell.*;
import rendszerfejlesztes.service.EventManager;
import rendszerfejlesztes.service.TicketManager;
import rendszerfejlesztes.service.impl.EventManagerImpl;
import rendszerfejlesztes.service.impl.TicketManagerImpl;

import java.util.List;

public class Updater {

    public static void updateTicket(Ticket ticket) {
        if( ticket.getSector() == null ) {
            TicketManager manager = new TicketManagerImpl();
            Sector sector = manager.getSectorByTicket(ticket);
            updateSector(sector);
            ticket.setSector( sector );
        }
    }

    public static void updateSector(Sector sector) {
        if( sector.getEvent() == null ) {
            EventManager manager = new EventManagerImpl();
            Event event = manager.getEventBySector(sector);
            sector.setEvent( event );
        }
    }

    public static void updateSubscription(Subscription subscription) {
        if( subscription.getEvent() == null ) {
            EventManager manager = new EventManagerImpl();
            Event event = manager.getEventBySubscription(subscription);
            subscription.setEvent( event );
        }
    }

}
