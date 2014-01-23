package org.localstorm.mcc.ejb.cashflow.impl;

import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.JointConnector;

import javax.ejb.Stateless;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author localstorm
 */
@Stateless
public class PriceTickerManagerBean implements PriceTickerManagerLocal {
    private long lastUpdate = 0;
    private Map<String, Price> prices = new ConcurrentSkipListMap<String, Price>();
    private static final long REFRESH_TRESHOLD = 60000;

    public PriceTickerManagerBean() {

    }

    @Override
    public Map<String, Price> getCurrentPrices() throws Exception {
        if (System.currentTimeMillis() - lastUpdate > REFRESH_TRESHOLD) {
            JointConnector jc = new JointConnector();
            Map<String, Price> res = jc.fetch();
            prices.clear();
            prices.putAll(res);
            lastUpdate = System.currentTimeMillis();
        }

        return prices;
    }
}
