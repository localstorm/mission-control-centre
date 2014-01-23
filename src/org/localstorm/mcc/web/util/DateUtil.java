package org.localstorm.mcc.web.util;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author localstorm
 */
public class DateUtil {

    public static Date parse(String s, DateFormat df) throws Exception {
        if (s!=null) {
            return df.parse(s);
        }
        return null;
    }

    public static String format(Date date, DateFormat sdf) throws Exception
    {
        if (date!=null) {
            return sdf.format(date);
        } else {
            return "";
        }
    }
}
