package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/DebtHistoryReport")
public class DebtHisotryReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution filling() {
        ReturnPageBean rpb = new ReturnPageBean(Pages.DEBT_HISTORY.toString());
        super.setReturnPageBean(rpb);
        return new ForwardResolution(Views.DEBT_HISTORY);
    }

}