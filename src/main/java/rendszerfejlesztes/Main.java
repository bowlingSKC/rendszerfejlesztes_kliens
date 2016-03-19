package rendszerfejlesztes;

import rendszerfejlesztes.modell.*;
import rendszerfejlesztes.service.EventManager;
import rendszerfejlesztes.service.TicketManager;
import rendszerfejlesztes.service.UserManager;
import rendszerfejlesztes.service.impl.EventManagerImpl;
import rendszerfejlesztes.service.impl.TicketManagerImpl;
import rendszerfejlesztes.service.impl.UserManagerImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Main {

    private static final EventManager eventManager = new EventManagerImpl();
    private static final UserManager userManager = new UserManagerImpl();
    private static final TicketManager ticketManager = new TicketManagerImpl();

    private User loggedUser;

    public void start() {
        printWelcomeMessage();

        boolean isExited = false;
        do {
            try {
                String selected;
                if( loggedUser == null ) {
                    printGuestMenu();
                    System.out.print("A valasztott menupont: ");
                    selected = Util.readStringFromCmd();
                    handleGusetBrowser(selected);
                } else if( loggedUser.getPrivilage() == Constants.USER_PRIVILAGE_ID ) {
                    printLoggedMenu();
                    System.out.print("A valasztott menupont: ");
                    selected = Util.readStringFromCmd();
                    handleLoggedBrowser(selected);
                } else if( loggedUser.getPrivilage() == Constants.ADMIN_PRIVILAGE_ID ) {
                    printAdminMenu();
                    System.out.print("A valasztott menupont: ");
                    selected = Util.readStringFromCmd();
                    handleAdminBrowser(selected);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } while (!isExited);

        printByeMessage();
    }

    private void printGuestMenu() {
        System.out.println("1.\tProgramok kozotti kereses");
        System.out.println("2.\tBejelentkezes");
        System.out.println("3.\tKilepes");
    }

    private void handleGusetBrowser(String selected) {
        switch (selected) {
            case "1":
                searchEvent();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                printByeMessage();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
        }
    }

    private void loginUser() {
        try {
            System.out.print("E-mail cim:\t");
            String email = Util.readStringFromCmd();
            System.out.print("Jelszo:\t\t");
            String pswd = Util.readStringFromCmd();

            User user = userManager.login(email, Util.hashPassword(pswd));
            if( user == null ) {
                System.out.println("Hibas felhasznalonev es/vagy jelszo!");
            } else {
                this.loggedUser = user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchEvent() {
        try {
            Event event = new Event();
            event.setPerformer(new Performer());
            System.out.print("Esemeny neve: ");
            event.setName( Util.readStringFromCmd() );
            System.out.print("Eloado neve: ");
            event.getPerformer().setName( Util.readStringFromCmd() );

            System.out.print("Szeretne hely alapjan keresni? (i/n)");
            String searcyByLocation = Util.readStringFromCmd();
            if( searcyByLocation.toLowerCase().equals("i") ) {
                event.setLocation(new Location());
                List<Location> locations = eventManager.getAllLocations();
                for(int i = 0; i < locations.size(); i++) {
                    System.out.println("\t" + (i+1) + ". - " + locations.get(i).getName());
                }
                try {
                    event.getLocation().setId( Util.readIntFromCmd() - 1 );
                } catch (NumberFormatException ex) {
                    // valamit cisnáni kell, merrt nem számot írt be
                }
            }

            List<Event> events = eventManager.searchEvent(event);
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

    private void showEvent(Event event) {
        System.out.println("Esemeny neve:   " + event.getName());
        System.out.println("Eloado:         " + event.getPerformer().getName());
        System.out.println("Kezdes:         " + Constants.DATE_FORMAT.format(event.getStart()));
        System.out.println("Befejezes:      " + Constants.DATE_FORMAT.format(Util.addMinsToDate(event.getStart(), event.getDuration())));
        System.out.println("Jegyar:         " + event.getPrice());
        System.out.println("Leiras:");
        System.out.println(event.getDescription());
        System.out.println("\n(F)oglalas\t(V)issza");

        try {
            String action = Util.readStringFromCmd().toLowerCase();
            if(action.equals("f")) {

                System.out.println(" ***  Szinpad  *** ");
                for(int i = 0; i < event.getSectorList().size(); i++) {
                    System.out.println((i+1) + ". szektor");
                }

                System.out.print("Melyik szektorba szeretne jegyet? ");
                int selectedSector = Util.readIntFromCmd();

                if( event.isSeats() ) {
                    System.out.println("ezt kesobb ...");
                } else {
                    Ticket ticket = new Ticket();
                    ticket.setSector( event.getSectorList().get(selectedSector-1) );
                    ticket.setBookedTime(new Date());
                    ticket.setPaid(false);
                    ticket.setStatus(2);
                    ticket.setUser(loggedUser);

                    Ticket db = ticketManager.bookTicket(loggedUser, ticket);
                    if( db != null ) {
                        System.out.println("Sikeresen lefoglalta a jegyet!");
                        loggedUser.getTickets().add(db);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printLoggedMenu() {
        System.out.println("1.\tProgamok kozotti kereses");
        System.out.println("2.\tJegyeim");
        System.out.println("X.\tKijelentkezes");
        System.out.println("0.\tKilepes");
    }

    private void handleLoggedBrowser(String selected) {
        switch (selected.toLowerCase()) {
            case "1":
                searchEvent();
                break;
            case "0":
                printByeMessage();
                break;
            case "2":
                showMyTickets();
                break;
            case "x":
                logout();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
        }
    }

    private void showMyTickets() {
        List<Ticket> tickets = ticketManager.getTicketByUser(loggedUser);
        for(int i = 0; i < tickets.size(); i++) {
            Updater.updateTicket(tickets.get(i));
            System.out.println( (i+1) + ". " + tickets.get(i).getSector().getEvent().getName() + " - " +
                    tickets.get(i).getSector().getEvent().getStart() );
        }
        System.out.println("(M)egtekint\t(V)issza");
        try {
            String response = Util.readStringFromCmd();
            if( response.toLowerCase().equals("m") ) {
                System.out.println("Jegy szama: ");
                int selected = Util.readIntFromCmd();
                System.out.println( tickets.get(selected - 1) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void logout() {
        this.loggedUser = null;
        System.out.println("**********************************************************************");
        System.out.println("                                Viszlat!                              ");
    }

    private void printAdminMenu() {
        System.out.println("1.\tProgamok kozotti kereses");
        System.out.println("2.\tFelhasznalok listazasa");
        System.out.println("3.\tFelhasznalok kozotti kereses");
        System.out.println("X.\tKijelentkezes");
        System.out.println("0.\tKilepes");
    }

    private void handleAdminBrowser(String selected) {
        switch (selected) {
            case "1":
                searchEvent();
                break;
            case "2":
                listUsers();
                break;
            case "3":
                searchUser();
                break;
            case "x":
                logout();
                break;
            case "0":
                printByeMessage();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
        }
    }

    private void searchUser() {
        try {
            User user = new User();
            System.out.print("Nev:      ");
            user.setName( Util.readStringFromCmd() );
            System.out.print("E-mail:   ");
            user.setEmail( Util.readStringFromCmd() );

            List<User> users = userManager.searchUser(user);
            for( User u : users ) {
                System.out.println(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printWelcomeMessage() {
        System.out.println("**********************************************************************");
        System.out.println("*                           13. VeszpremFest                         *");
        System.out.println("*                          2016. julius 13-16.                       *");
        System.out.println("**********************************************************************");
    }

    private void printByeMessage() {
        System.out.println("**********************************************************************");
        System.out.println("                                Viszlat!                              ");
        System.exit(0);
    }

    private void listUsers(){
        List<User> users = userManager.getAllUser();
        for(User usr : users){
            System.out.println(usr.toNiceString());
        }

    }

    public static void main(String[] args) {
        new Main().start();
    }

}