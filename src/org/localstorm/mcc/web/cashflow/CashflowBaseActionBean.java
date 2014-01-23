package org.localstorm.mcc.web.cashflow;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.*;
import org.localstorm.mcc.web.BaseActionBean;

/**
 *
 * @author localstorm
 */
public class CashflowBaseActionBean extends BaseActionBean {

    public AssetManager getAssetManager() {
        return ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
    }

    public OperationManager getOperationManager() {
        return ContextLookup.lookup(OperationManager.class, OperationManager.BEAN_NAME);
    }

    public HistoricalValuesManager getHistoricalValuesManager() {
        return ContextLookup.lookup(HistoricalValuesManager.class,
                                    HistoricalValuesManager.BEAN_NAME);
    }

    public TargetManager getTargetManager() {
        return ContextLookup.lookup(TargetManager.class, TargetManager.BEAN_NAME);
    }

    public PriceTickerManager getPriceTickerManager() {
        return ContextLookup.lookup(PriceTickerManager.class, PriceTickerManager.BEAN_NAME);
    }
}
