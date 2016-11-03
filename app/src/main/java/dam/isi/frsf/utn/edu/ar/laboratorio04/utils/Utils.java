package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import java.util.Calendar;

/**
 * Created by Pablo on 31/10/2016.
 */

public class Utils {
    public static Calendar getTimeAfterInSecs(int secs) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,secs);
        return cal;
    }

}
