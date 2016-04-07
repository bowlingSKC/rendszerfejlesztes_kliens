package rendszerfejlesztes.controllers;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.Event;
import rendszerfejlesztes.modell.Ticket;
import rendszerfejlesztes.modell.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static void findPath(){
        List<Ticket> tickets = Main.getTicketManager().getTicketByUser(Main.getLoggedUser());
        for (Ticket tic : tickets){
            Updater.updateTicket(tic);
        }
        List<Event> events = new ArrayList<>();

        //SORBARENDEZES
        class LexicographicComparator implements Comparator<Ticket> {
            @Override
            public int compare(Ticket a, Ticket b) {
                Long date1 = a.getSector().getEvent().getStart().getTime();
                Long date2 = b.getSector().getEvent().getStart().getTime();

                return date1.compareTo(date2);
            }
        }
        Collections.sort(tickets, new LexicographicComparator());

        for (Ticket tic : tickets){
            //System.out.println(tic.printTicketPath());
            if(!events.contains(tic.getSector().getEvent())){
                events.add(tic.getSector().getEvent());
            }
        }
        //KIIRATAS
        System.out.println("");
        for(int i = 0; i < events.size(); i++){
            if(i < events.size()-1){
                long tempTime = events.get(i).getStart().getTime() + 1000*60*events.get(i).getDuration();
                events.get(i).getStart().getDay();
                System.out.println(i+1 + ".\t" + events.get(i).printPath());
                if(tempTime > events.get(i+1).getStart().getTime()){
                    System.out.println("\t\tUtkozes: " + events.get(i).getName() + " - " + events.get(i+1).getName());
                }
            }else{
                System.out.println(i+1 + ".\t" + events.get(i).printPath() + "\n");
            }
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
                        if(tickets.size() > 0){
                            for(int j = 0; j < tickets.size(); j++) {
                                Updater.updateTicket(tickets.get(j));
                                if(tickets.get(j).isPaid()){
                                    tickets.remove(j);
                                    j--;
                                }
                            }
                            for(int j = 0; j < tickets.size(); j++) {
                                Updater.updateTicket(tickets.get(j));
                                System.out.print((j + 1) + ".\tEsemeny: " + tickets.get(j).getSector().getEvent().getName() + "\tDatum: " +
                                        Constants.DATE_FORMAT.format(tickets.get(j).getSector().getEvent().getStart()) + "\tSzektor: " +
                                        tickets.get(j).getSector().getDepth());
                                if(tickets.get(j).getCol() != null){
                                    System.out.print("\tSor: " + tickets.get(j).getRow() + "\tOszlop: " +
                                            tickets.get(j).getCol());
                                }
                                System.out.println("\tAr: " + tickets.get(j).getSector().getPrice() + " Ft");
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
                            //KIVALASZTOTTAK FELDOLGOZASA
                            if(selectedTickets.size() > 0){
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
                                    sum += tic.getSector().getPrice();
                                    //sum += Main.getTicketManager().getSectorByTicket(tic).getPrice();
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
                                    System.out.println("Visszautasitottad a fizetest!");
                                }
                            }

                        }else{
                            System.out.println("Nincs jegye az adott felhasznalonak: " + users.get(i).getName() + " - " +
                                    users.get(i).getEmail());
                        }
                    }
                }
            }else{
                System.out.println("Nincs felhasznalo az adott parameterek szerint!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
