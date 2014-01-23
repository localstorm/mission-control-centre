package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.PriceTickerManager;
import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.tools.aop.runtime.Logged;

import java.util.Map;

/**
 * @author localstorm
 * @secure-by session (no security check)
 */
@UrlBinding("/actions/cash/nil/MarketPricesReport")
public class MarketPricesViewActionBean extends CashflowBaseActionBean {

    public Map<String, Price> symbols;

    public Map<String, Price> getSymbols() {
        return symbols;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() {
        PriceTickerManager ptm = super.getPriceTickerManager();

        try {
            symbols = ptm.getCurrentPrices();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ForwardResolution(Views.MARKET_PRICES);
    }

}