package org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.i18n;

import org.localstorm.mcc.ejb.cashflow.impl.ticker.PriceParser;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:33:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class RussianPriceParser implements PriceParser {
    public BigDecimal parse(String s) throws NumberFormatException {
        try {
            s = s.replaceAll("\\.", "").replaceAll(",", ".").replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "");
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(s);
        }
    }
}
