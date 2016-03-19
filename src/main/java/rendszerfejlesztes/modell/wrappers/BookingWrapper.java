package rendszerfejlesztes.modell.wrappers;

import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

public class BookingWrapper {

    private User user;
    private Ticket ticket;

    public BookingWrapper() {

    }

    public BookingWrapper(User user, Ticket ticket) {
        this.user = user;
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
