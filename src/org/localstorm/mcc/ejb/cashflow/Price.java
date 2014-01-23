package org.localstorm.mcc.ejb.cashflow;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: Feb 26, 2011
 * Time: 1:12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Price {
    private BigDecimal buy;
    private BigDecimal sell;

    public Price(BigDecimal buy, BigDecimal sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getSpread() {
        if (buy.equals(sell)){
            return BigDecimal.ZERO;
        } else {
            return (buy.subtract(sell).divide(sell.add(buy), MoneyMathContext.ROUNDING).multiply(new BigDecimal(200)));
        }
    }

    public String toString()
    {
        return buy.toString()+"/"+sell.toString();
    }
}
