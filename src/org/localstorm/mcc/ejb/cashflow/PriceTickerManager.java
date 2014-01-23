package org.localstorm.mcc.ejb.cashflow;

import java.util.Map;

/**
 * @author localstorm
 */

public interface PriceTickerManager
{
    public static final String BEAN_NAME="PriceTickerManagerBean";

    public Map<String, Price> getCurrentPrices() throws Exception;
}
