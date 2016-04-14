package rendszerfejlesztes.controllers;


import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.Util;
import rendszerfejlesztes.modell.Subscription;

import java.io.IOException;
import java.util.List;

public class SubscribeController {

    public static void showSubscriptions(){
        List<Subscription> subscriptions = Main.getSubscribeManager().getUserSubscription(Main.getLoggedUser());
        for(int i = 0; i < subscriptions.size(); i++){
            Updater.updateSubscription(subscriptions.get(i));
            System.out.println(i+1 + ". " + subscriptions.get(i).getEvent().getName() + "\t" +
                    Constants.DATE_FORMAT.format(subscriptions.get(i).getEvent().getStart()));
        }
        System.out.println("(L)eiratkozas\t((V)issza");
        try {
            String response = Util.readStringFromCmd();
            if( response.toLowerCase().equals("l") ) {
                System.out.println("Feliratkozas szama: ");
                int selected = Util.readIntFromCmd();
                for(int i = 0; i < subscriptions.size(); i++){
                    if(i == selected-1){
                        if(Main.getSubscribeManager().unSubscribe(subscriptions.get(i))){
                            System.out.println("Leiratkozas sikeres!");
                        }else{
                            System.out.println("Leiratkozas sikertelen!");
                        }
                    }
                }
                showSubscriptions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
