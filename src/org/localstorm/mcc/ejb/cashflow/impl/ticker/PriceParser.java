package org.localstorm.mcc.ejb.cashflow.impl.ticker;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:33:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PriceParser {

    BigDecimal parse(String s) throws NumberFormatException;
}
