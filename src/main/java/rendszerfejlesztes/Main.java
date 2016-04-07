package rendszerfejlesztes;

import rendszerfejlesztes.controllers.EventController;
import rendszerfejlesztes.controllers.SubscribeController;
import rendszerfejlesztes.controllers.UserController;
import rendszerfejlesztes.controllers.TicketController;
import rendszerfejlesztes.modell.Subscription;
import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.service.*;
import rendszerfejlesztes.service.impl.*;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final EventManager eventManager = new EventManagerImpl();
    private static final UserManager userManager = new UserManagerImpl();
    private static final SubscribeManager subscribeManager = new SubscribeManagerImpl();

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static TicketManager getTicketManager() {
        return ticketManager;
    }

    public static SubscribeManager getSubscribeManager() {return subscribeManager; }

    private static final TicketManager ticketManager = new TicketManagerImpl();
    private static final TestConnection testConnection = new TestConnectionImpl();

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User _loggedUser) {
        loggedUser = _loggedUser;
    }

    public void start() {
        testConnection();

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

    private void testConnection() {
        try {
            testConnection.ping();
        } catch (Exception ex) {
            System.out.println("Nem lehet felvenni a kapcsolatot a szerverrel! Probalja kesobb!");
            System.exit(0);
        }
    }

    private void printGuestMenu() {
        System.out.println("1.\tProgramok kozotti kereses");
        System.out.println("2.\tBejelentkezes");
        System.out.println("3.\tKilepes");
    }

    private void handleGusetBrowser(String selected) {
        switch (selected) {
            case "1":
                EventController.searchEvent();
                break;
            case "2":
                UserController.login();
                break;
            case "3":
                printByeMessage();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
        }
    }

    private void printLoggedMenu() {
        System.out.println("1.\tProgamok kozotti kereses");
        System.out.println("2.\tJegyeim");
        System.out.println("3.\tUtvonal terv");
        System.out.println("4.\tFeliratkozasok");
        System.out.println("X.\tKijelentkezes");
        System.out.println("0.\tKilepes");
    }

    private void handleLoggedBrowser(String selected) {
        switch (selected.toLowerCase()) {
            case "1":
                EventController.searchEvent();
                break;
            case "0":
                printByeMessage();
                break;
            case "2":
                UserController.showMyTickets();
                break;
            case "3":
                TicketController.findPath();
                break;
            case "4":
                SubscribeController.showSubscriptions();
                break;
            case "x":
                UserController.logout();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
        }
    }

    private void printAdminMenu() {
        System.out.println("1.\tProgamok kozotti kereses");
        System.out.println("2.\tFelhasznalok listazasa");
        System.out.println("3.\tFelhasznalok kozotti kereses");
        System.out.println("4.\tJegyek kiadasa");
        System.out.println("X.\tKijelentkezes");
        System.out.println("0.\tKilepes");
    }

    private void handleAdminBrowser(String selected) {
        switch (selected) {
            case "1":
                EventController.searchEvent();
                break;
            case "2":
                listUsers();
                break;
            case "3":
                UserController.searchUser();
                break;
            case "4":
                TicketController.handOutTicket();
                break;
            case "x":
                UserController.logout();
                break;
            case "0":
                printByeMessage();
                break;
            default:
                System.out.println("Nincs ilyen menupont!");
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