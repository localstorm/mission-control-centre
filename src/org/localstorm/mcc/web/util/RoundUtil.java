package org.localstorm.mcc.web.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author localstorm
 */
public class RoundUtil {

    public static BigDecimal round(BigDecimal val, MathContext rounding) {
        if (val==null) {
            return null;
        } else {
            return val.round(rounding);
        }
    }
    
}
