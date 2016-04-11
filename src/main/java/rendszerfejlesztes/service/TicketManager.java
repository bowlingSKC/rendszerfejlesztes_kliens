package rendszerfejlesztes.service;

import rendszerfejlesztes.modell.Discount;
import rendszerfejlesztes.modell.Sector;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.util.List;

public interface TicketManager {

    List<Ticket> getTicketByUser(User user);

    Sector getSectorByTicket(Ticket ticket);

    Ticket bookTicket(Ticket ticket);

    boolean removeTickets(Ticket ticket);

    Ticket setPaidTrue(Ticket ticket);

    boolean updateDiscount(Discount discount, Integer discount_id);

    List<Discount> getAllDiscount();
}
