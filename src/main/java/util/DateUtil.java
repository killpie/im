package util;

import org.joda.time.DateTime;

/**
 * @author killpie
 * @date 2018/12/15 18:44
 **/
public class DateUtil {

    public static String getDateTime(){
        return DateTime.now().toString("MM/dd/yyyy hh:mm:ss.SSSa");
    }

}
