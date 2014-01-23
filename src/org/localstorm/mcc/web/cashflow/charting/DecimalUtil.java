/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.cashflow.charting;

import java.math.BigDecimal;

/**
 *
 * @author localstorm
 */
public class DecimalUtil {
    public static BigDecimal nvl(BigDecimal cost, BigDecimal defaultCost)
    {
        return (cost==null) ? defaultCost : cost;
    }
}
