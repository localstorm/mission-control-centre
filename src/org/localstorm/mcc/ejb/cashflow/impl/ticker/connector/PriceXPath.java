package org.localstorm.mcc.ejb.cashflow.impl.ticker.connector;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:23:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PriceXPath {

    private final String buyXPath;
    private final String sellXPath;

    public PriceXPath(String buyXPath, String sellXPath)
    {
        this.buyXPath = buyXPath;
        this.sellXPath = sellXPath;
    }

    public String getBuyXPath() {
        return buyXPath;
    }

    public String getSellXPath() {
        return sellXPath;
    }
}
