package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.Discount;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.io.IOException;
import java.util.ArrayList;
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
        List<Ticket> tickets = Main.getTicketManager().getTicketByUser(Main.getLoggedUser());
        for(int i = 0; i < tickets.size(); i++) {
            Updater.updateTicket(tickets.get(i));
            System.out.print((i + 1) + ".\tEsemeny: " + tickets.get(i).getSector().getEvent().getName() + "\tDatum: " +
                    Constants.DATE_FORMAT.format(tickets.get(i).getSector().getEvent().getStart()) + "\tSzektor: " +
                    tickets.get(i).getSector().getDepth());
            if(tickets.get(i).getCol() != null){
                System.out.print("\tSor: " + tickets.get(i).getRow() + "\tOszlop: " +
                        tickets.get(i).getCol());
            }
            System.out.print("\tAr: " + tickets.get(i).getSector().getPrice() * tickets.get(i).getDiscount().getValue() + " Ft");
            System.out.print("\t" + tickets.get(i).getDiscount().getName());
            if(tickets.get(i).isPaid() ) {
                System.out.println("\tFizetett");
            } else {
                System.out.println("\tNem fizetett");
            }
        }
        System.out.println("(M)egtekint\t(L)emond\t(K)edvezmenyek\t(V)issza");
        try {
            String response = Util.readStringFromCmd();
            if( response.toLowerCase().equals("m") ) {
                System.out.print("Jegy szama:   ");
                int selected = Util.readIntFromCmd();
                TicketController.showTicket(tickets.get(selected - 1));
            }
            if( response.toLowerCase().equals("l") ) {
                removeTicket();
            }
            if( response.toLowerCase().equals("k") ) {
                discounts();
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

    public static void removeTicket(){
        List<Ticket> tickets =  Main.getTicketManager().getTicketByUser(Main.getLoggedUser());
        System.out.print("Jegyek sorszama (kotojellel elvalasztva): ");
        String selected = null;
        try {
            selected = Util.readStringFromCmd();
            selected = selected.replaceAll("\\s+","");              //remove white spaces
            List<Integer> selectedTickets = new ArrayList<>();
            String[] parts = selected.split("-");
            try{
                for(int i = 0; parts[i] != null; i++){
                    selectedTickets.add(Integer.parseInt(parts[i]));
                }
            }catch (Exception e){
                //System.out.println("Catched.");
            }

            System.out.println("Biztos le akarja mondani a kovetkezoket? (I/N)");
            for(Integer szam : selectedTickets){
                System.out.print(szam + " ");
            }
            System.out.println("");
            List<Ticket> remTic = new ArrayList<>();
            if(Util.readStringFromCmd().toLowerCase().equals("i")){
                for(Integer szam : selectedTickets){
                   int index = 1;
                   for(Ticket tic : tickets){
                       if(index == szam){
                           if(Main.getTicketManager().removeTickets(tic)){
                               System.out.println("Deleted with id: " + szam.toString());
                               remTic.add(tic);
                           }else{
                               System.out.println("Not deleted with id: " + szam.toString());
                           }
                       }
                       index++;
                   }
                }
            }
            for(Ticket tic : remTic){
                Main.getLoggedUser().getTickets().remove(tic);
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Main.setLoggedUser( Main.getUserManager().updateUser(Main.getLoggedUser()));
    }

    public static void discounts(){
        List<Ticket> tickets = Main.getTicketManager().getTicketByUser(Main.getLoggedUser());
        try {
            List<Discount> discounts = Main.getTicketManager().getAllDiscount();
            System.out.print("Jegy szama:   ");
            int selected = Util.readIntFromCmd();
            Ticket updated = tickets.get(selected-1);
            System.out.println("\nValaszthato kedvezmenyek:\n\t");
            for(int i = 0; i < discounts.size(); i++){
                System.out.println((i+1) + ". " + discounts.get(i).getName());
            }
            System.out.println("Kivalasztott: "+tickets.get(selected-1));
            System.out.print("Valasztott kedvezmeny: ");
            selected = Util.readIntFromCmd();

            for(Discount dis : discounts){
                if(dis.getId() == selected){
                    if(Main.getTicketManager().updateDiscount(dis,updated.getId())){
                        System.out.println("Sikeres modositas!");
                    }else{
                        System.out.println("Sikertelen modositas!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
