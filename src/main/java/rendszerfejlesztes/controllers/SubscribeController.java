package rendszerfejlesztes.controllers;


import rendszerfejlesztes.Constants;
import rendszerfejlesztes.Main;
import rendszerfejlesztes.Updater;
import rendszerfejlesztes.modell.Subscription;

import java.util.List;

public class SubscribeController {

    public static void showSubscriptions(){
        List<Subscription> subscriptions = Main.getSubscribeManager().getUserSubscription(Main.getLoggedUser());
        for(int i = 0; i < subscriptions.size(); i++){
            Updater.updateSubscription(subscriptions.get(i));
            System.out.println(i+1 + ". " + subscriptions.get(i).getEvent().getName() + "\t" +
                    Constants.DATE_FORMAT.format(subscriptions.get(i).getEvent().getStart()));
        }
        System.out.println("");
    }
}
