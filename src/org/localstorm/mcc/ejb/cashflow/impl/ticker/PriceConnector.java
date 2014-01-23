package org.localstorm.mcc.ejb.cashflow.impl.ticker;

import org.localstorm.mcc.ejb.cashflow.Price;

import java.util.Map;

public interface PriceConnector {
    public Map<String, Price> fetch() throws Exception;
}
