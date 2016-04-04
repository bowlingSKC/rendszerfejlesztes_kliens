package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EventController {

    public static void searchEvent() {
        try {
            Event event = new Event();
            event.setPerformer(new Performer());
            System.out.print("Esemeny neve:     ");
            event.setName( Util.readStringFromCmd() );
            System.out.print("Eloado neve:      ");
            event.getPerformer().setName( Util.readStringFromCmd() );

            System.out.print("Szeretne hely alapjan keresni? (i/n)");
            String searcyByLocation = Util.readStringFromCmd();
            if( searcyByLocation.toLowerCase().equals("i") ) {
                event.setLocation(new Location());
                List<Location> locations = Main.getEventManager().getAllLocations();
                for(int i = 0; i < locations.size(); i++) {
                    System.out.println("\t" + (i+1) + ". - " + locations.get(i).getName());
                }
                try {
                    event.getLocation().setId( Util.readIntFromCmd() - 1 );
                } catch (NumberFormatException ex) {
                    // valamit cisnáni kell, merrt nem számot írt be
                }
            }

            List<Event> events = Main.getEventManager().searchEvent(event);
            System.out.println("A keresenek megfelelo esemenyek:");
            for(int i = 0; i < events.size(); i++) {
                System.out.println("\t" + (i+1) + " - " + events.get(i).getName() + " ["+ Constants.DATE_FORMAT.format(events.get(i).getStart()) +"] *** Eloado: " + events.get(i).getPerformer().getName() + " *** Helyszin: " + events.get(i).getLocation().getName());
            }

            System.out.println("Muveletek: (M)egtekint (V)issza");
            System.out.print("Valasztott muvelet: ");
            String action = Util.readStringFromCmd();
            if( action.toLowerCase().equals("m") ) {
                System.out.print("Eloadas sorszama: ");
                int nth = Util.readIntFromCmd();
                showEvent( events.get(nth-1) );
            } else if( action.toLowerCase().equals("v") ) {
                return;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void showEvent(Event event) {
        System.out.println("Esemeny neve:   " + event.getName());
        System.out.println("Eloado:         " + event.getPerformer().getName());
        System.out.println("Kezdes:         " + Constants.DATE_FORMAT.format(event.getStart()));
        System.out.println("Befejezes:      " + Constants.DATE_FORMAT.format(Util.addMinsToDate(event.getStart(), event.getDuration())));
        System.out.println("Leiras:");
        System.out.println(event.getDescription());
        System.out.println("\n(F)oglalas\t(V)issza");

        try {
            String action = Util.readStringFromCmd().toLowerCase();
            if(action.equals("f")) {
                bookEvent(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bookEvent(Event event) throws IOException {

        System.out.print("Hany darab jegyet szeretne foglalni? ");
        int numOfTicket = Util.readIntFromCmd();

        for(int i = 0; i < numOfTicket; i++) {
            System.out.println(" ***  Szinpad  *** ");
            for(int j = 0; j < event.getSectorList().size(); j++) {
                System.out.println((j+1) + ". szektor");
            }

            System.out.print("Melyik szektorba szeretne jegyet? ");
            int selectedSector = Util.readIntFromCmd();

            if( event.isSeats() ) {

                Sector selected = event.getSectorList().get(selectedSector - 1);

                selected.showSeats();
                System.out.print("Sor szama:    ");
                int row = Util.readIntFromCmd();
                System.out.print("Oszlop szama: ");
                int col = Util.readIntFromCmd();

                if( selected.isSeatReserved(col, row) ) {
                    System.out.println("A megadott szek mar le van foglalva");
                } else {
                    Ticket ticket = new Ticket();
                    ticket.setSector( new Sector(selected.getId()) );
                    ticket.setBookedTime(new Date());
                    ticket.setPaid(false);
                    ticket.setStatus(2);
                    ticket.setUser(new User(Main.getLoggedUser().getId()));
                    ticket.setRow(row);
                    ticket.setCol(col);

                    Ticket db = Main.getTicketManager().bookTicket(ticket);
                    if( db != null ) {
                        System.out.println("Sikeresen lefoglalta a jegyet!");
                        Main.getLoggedUser().getTickets().add(db);
                    }
                }

            } else {
                Ticket ticket = new Ticket();
                ticket.setSector( event.getSectorList().get(selectedSector-1) );
                ticket.setBookedTime(new Date());
                ticket.setPaid(false);
                ticket.setStatus(2);
                ticket.setUser(Main.getLoggedUser());


                Ticket db = Main.getTicketManager().bookTicket(ticket);
                if( db != null ) {
                    System.out.println("Sikeresen lefoglalta a jegyet!");
                    Main.getLoggedUser().getTickets().add(db);
                }

            }
        }

    }

}
