package org.localstorm.mcc.web.cashflow;

import org.localstorm.mcc.web.CommonSessionKeys;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface CashflowSessionKeys extends CommonSessionKeys
{
    public static final String ASSETS               = "assets";
    public static final String TARGETS              = "targets";
    public static final String ACCESSIBLE_ASSETS_MAP = "accessibleAssets";
    public static final String ACCESSIBLE_TARGETS_MAP= "accessibleTargets";
}
