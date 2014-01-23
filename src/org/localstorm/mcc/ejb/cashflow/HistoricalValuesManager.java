package org.localstorm.mcc.ejb.cashflow;

import org.localstorm.mcc.ejb.cashflow.entity.ValueType;
import org.localstorm.mcc.ejb.cashflow.entity.HistoricalValue;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import org.localstorm.mcc.ejb.users.User;


/**
 * @author localstorm
 */
public interface HistoricalValuesManager
{
    public static final String BEAN_NAME="HistoricalValuesManagerBean";

    public HistoricalValue getLastHistoricalValue(ValueType vt,
                                                  BigDecimal defaultValue,
                                                  User user);

    public void log(HistoricalValue hv);

    public Collection<HistoricalValue> getHistory(ValueType valueTag,
                                                  User user,
                                                  Date minDate);

    public Collection<HistoricalValue> getHistory(ValueType valueTag,
                                                  Integer objectId,
                                                  User user,
                                                  Date minDate);

    public void truncateHistory(ValueType valueTag,
                                Integer objectId,
                                User user,
                                Date maxDate);
}
