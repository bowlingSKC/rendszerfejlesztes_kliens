package rendszerfejlesztes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface Constants {

    int USER_PRIVILAGE_ID = 1;
    int ADMIN_PRIVILAGE_ID = 2;

    DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss");     // dátumok formázása

    int TICKET_OPTIONAL = 1;
    int TICKET_BOOKED = 2;

}
