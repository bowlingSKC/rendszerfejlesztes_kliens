package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.io.IOException;
import java.util.List;

public class UserController {

    public static void login() {
        try {
            System.out.print("E-mail cim:   ");
            String email = Util.readStringFromCmd();
            System.out.print("Jelszo:       ");
            String pswd = Util.readStringFromCmd();

            User user = Main.getUserManager().login(email, Util.hashPassword(pswd));
            if( user == null ) {
                System.out.println("Hibas felhasznalonev es/vagy jelszo!");
            } else {
                System.out.println("Sikeres bejelentkezes!");
                Main.setLoggedUser(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void showMyTickets() {
        List<Ticket> tickets = Main.getLoggedUser().getTickets();
        for(int i = 0; i < tickets.size(); i++) {
            Updater.updateTicket(tickets.get(i));
            System.out.println( (i+1) + ". " + tickets.get(i).getSector().getEvent().getName() + " - " +
                    Constants.DATE_FORMAT.format(tickets.get(i).getSector().getEvent().getStart()) );
        }
        System.out.println("(M)egtekint\t(V)issza");
        try {
            String response = Util.readStringFromCmd();
            if( response.toLowerCase().equals("m") ) {
                System.out.print("Jegy szama:   ");
                int selected = Util.readIntFromCmd();
                TicketContoller.showTicket( tickets.get( selected - 1 ) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        Main.setLoggedUser(null);
        System.out.println("**********************************************************************");
        System.out.println("                                Viszlat!                              ");
    }

    public static void searchUser() {
        try {
            User user = new User();
            System.out.print("Nev:      ");
            user.setName( Util.readStringFromCmd() );
            System.out.print("E-mail:   ");
            user.setEmail( Util.readStringFromCmd() );

            List<User> users = Main.getUserManager().searchUser(user);
            for( User u : users ) {
                System.out.println(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
