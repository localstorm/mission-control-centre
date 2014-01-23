package org.localstorm.mcc.ejb.cashflow;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author localstorm
 */
public class MoneyMathContext {
    public final static MathContext ROUNDING = new MathContext(15, RoundingMode.FLOOR);
}
