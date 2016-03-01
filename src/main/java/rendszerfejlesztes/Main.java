package rendszerfejlesztes;

import rendszerfejlesztes.modell.User;
import rendszerfejlesztes.service.UserManager;
import rendszerfejlesztes.service.impl.UserManagerImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            User user = new User();
            System.out.print( "Username: " );
            user.setUser( Util.readStringFromCmd() );
            System.out.print("Password: ");
            user.setPassword( Util.readStringFromCmd() );

            UserManager userManager = new UserManagerImpl();
            long start = System.currentTimeMillis();
            userManager.create(user);
            long end = System.currentTimeMillis();

            System.out.println("Mentve! A muvelet " + (end - start) + " msec alatt fejezodott be!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
