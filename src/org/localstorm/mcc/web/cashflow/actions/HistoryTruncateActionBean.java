package org.localstorm.mcc.web.cashflow.actions;

import java.util.Calendar;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.HistoricalValuesManager;
import org.localstorm.mcc.ejb.cashflow.entity.ValueType;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/TruncateHistory")
public class HistoryTruncateActionBean extends CashflowBaseActionBean
{
    @Validate(required=true)
    private String valueTag;

    @Validate(required=true)
    private int keepDays;

    private Integer objectId;

    public Integer getObjectId()
    {
        return objectId;
    }

    public int getKeepDays()
    {
        return keepDays;
    }

    public String getValueTag()
    {
        return valueTag;
    }

    public void setValueTag(String valueTag)
    {
        this.valueTag = valueTag;
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
    }

    public void setKeepDays(int keepDays)
    {
        this.keepDays = keepDays;
    }
    
    @DefaultHandler
    @Logged
    public Resolution filling() {
        User user                   = super.getUser();
        HistoricalValuesManager hvm = super.getHistoricalValuesManager();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -this.getKeepDays());

        String[] tags = this.getValueTag().split(",");

        for (String vTag: tags) {
            hvm.truncateHistory(ValueType.valueOf(vTag),
                                this.getObjectId(),
                                user,
                                cal.getTime());
        }

        ReturnPageBean rpb = super.getReturnPageBean();
        return NextDestinationUtil.getRedirection(rpb);
    }

}
