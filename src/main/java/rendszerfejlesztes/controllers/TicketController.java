package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.util.ArrayList;
import java.util.List;

public class TicketController {

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

    public static void handOutTicket(){
        try{
            User user = new User();
            System.out.print("Nev: ");
            user.setName( Util.readStringFromCmd() );
            System.out.print("E-mail: ");
            user.setEmail( Util.readStringFromCmd() );
            List<User> users = Main.getUserManager().searchUser(user);
            /*List<User> tempusers = Main.getUserManager().getAllUser();
            List<User> users = new ArrayList<>();
            for(User usr : tempusers){
                if(usr.getName().toLowerCase().contains(user.getName().toLowerCase()) || usr.getEmail().contains(user.getEmail())){
                    users.add(usr);
                }
            }*/
            for(int i = 0; i < users.size(); i++) {
                System.out.println( (i+1) + ". Nev: " + users.get(i).getName() + " - Email: " +  users.get(i).getEmail());
            }
            if(users.size() > 0){
                System.out.print("Felhasznalo szama: ");
                int selected = Util.readIntFromCmd();
                for(int i = 0; i < users.size(); i++) {
                    if(i == selected-1){
                        List<Ticket> tickets = users.get(i).getTickets();
                        int count = 0;
                        for(int j = 0; j < tickets.size(); j++) {
                            Updater.updateTicket(tickets.get(j));
                            if(tickets.get(j).isPaid()){
                                tickets.remove(j);
                            }else{
                                count++;
                                System.out.println( (count) + ". Esemeny: " + tickets.get(j).getSector().getEvent().getName() + " Datum: " +
                                        Constants.DATE_FORMAT.format(tickets.get(j).getSector().getEvent().getStart()) + " Sektor: " +
                                        tickets.get(j).getSector().getId() + " Sor: " + tickets.get(j).getRow() + " Oszlop: " +
                                        tickets.get(j).getCol() );
                            }
                        }

                        //SORSZAMOK OLVASASA
                        System.out.print("Jegyek sorszama (kotojellel elvalasztva): ");
                        String input = Util.readStringFromCmd();
                        input = input.replaceAll("\\s+","");              //remove white spaces
                        List<Integer> selectedTickets = new ArrayList<>();
                        String[] parts = input.split("-");
                        try{
                            for(int j = 0; parts[j] != null; j++){
                                selectedTickets.add(Integer.parseInt(parts[j]));
                            }
                        }catch (Exception e){
                            //System.out.println("Catched.");
                        }
                        System.out.print("Kivalasztottak: ");
                        for(Integer szam : selectedTickets){
                            System.out.print(szam + " ");
                        }
                        //VEGOSSZEG SZAMOLAS
                        List<Ticket> wishedTickets = new ArrayList<>();
                        for(int j = 0; j < tickets.size(); j++) {
                            if(selectedTickets.contains(j+1)){
                                wishedTickets.add(tickets.get(j));
                            }
                        }
                        int sum = 0;
                        for(Ticket tic : wishedTickets){
                            sum += Main.getTicketManager().getSectorByTicket(tic).getPrice();    //TODO: NEED TO CONFIGURE PRICE
                        }
                        System.out.println("Vegosszeg: " + sum + " Ft   Jovahagyja a fizetest? (I/N)");

                        if(Util.readStringFromCmd().toLowerCase().equals("i")){
                            for(Integer index : selectedTickets){
                                for(int j = 0; j < tickets.size(); j++) {
                                    if(j == index-1){
                                        Ticket back = Main.getTicketManager().setPaidTrue(tickets.get(j));
                                        if(back == null){
                                            System.out.println("Hiba a feldolgozas kozben: " + tickets.get(j).toString());
                                        }else{
                                            System.out.println("Feldolgozas sikeres: " + tickets.get(j).toString());
                                        }
                                    }
                                }
                            }
                        }else{
                            System.out.println("Visszautasítottad a fizetest!");
                        }
                    }
                }
            }else{
                System.out.print("Nincs felhasznalo az adott parameterek szerint!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
