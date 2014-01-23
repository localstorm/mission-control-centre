package org.localstorm.mcc.ejb.cashflow.impl.ticker.connector;

import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.PriceConnector;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.institutions.NomosMetalsAndCCYs;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.institutions.Sberbank;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: 2/26/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class JointConnector implements PriceConnector {
    private List<PriceConnector> cons;
    private ConcurrentSkipListMap<String, Price> prices;

    public JointConnector() {
        this.cons = new CopyOnWriteArrayList<PriceConnector>();
        this.cons.add(new Sberbank());
        this.cons.add(new NomosMetalsAndCCYs());
        this.prices = new ConcurrentSkipListMap<String, Price>();
    }

    @Override
    public Map<String, Price> fetch() throws Exception {
        this.prices.clear();
        for (PriceConnector c: cons) {
            this.prices.putAll(c.fetch());
        }
        return this.prices;
    }
}
