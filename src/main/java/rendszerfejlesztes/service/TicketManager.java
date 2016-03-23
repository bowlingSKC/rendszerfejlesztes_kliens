package rendszerfejlesztes.service;

import rendszerfejlesztes.modell.Sector;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.util.List;

public interface TicketManager {

    List<Ticket> getTicketByUser(User user);

    Sector getSectorByTicket(Ticket ticket);

    Ticket bookTicket(User user, Ticket ticket);

    boolean removeTickets(Ticket ticket);

}
