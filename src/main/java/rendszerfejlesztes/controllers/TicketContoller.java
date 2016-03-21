package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.modell.Ticket;

public class TicketContoller {

    public static void showTicket(Ticket ticket) {
        System.out.println("Eloadas:    " + ticket.getSector().getEvent().getName());
        System.out.println("Kezdes:     " + Constants.DATE_FORMAT.format(ticket.getSector().getEvent().getStart()));

        if( ticket.isPaid() ) {
            System.out.println("Fizetett?   igen");
        } else {
            System.out.println("Fizetett?   nem");
        }

        if( ticket.getRow() != null && ticket.getCol() != null ) {
            System.out.println("Sor:    "  + ticket.getRow());
            System.out.println("Oszlop: " + ticket.getCol());
        }
    }

}
