package rendszerfejlesztes;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static String readStringFromCmd() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String value = br.readLine();
        return value;
    }

    public static String readPasswordFromCmd() throws IOException {
        Console con = System.console();
        char[] pswd = con.readPassword();
        return new String(pswd);
    }

    public static Integer readIntFromCmd() throws IOException, NumberFormatException {
        return Integer.parseInt(readStringFromCmd());
    }

    public static Date addMinsToDate(Date date, int minutes) {
        long time = date.getTime();
        Date after = new Date(time + (minutes * 60000) );
        return after;
    }

    public static String hashPassword(String plain) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(plain.getBytes("UTF-8"));
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

}
