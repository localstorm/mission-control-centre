package org.localstorm.mcc.web.gtd;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.gtd.GtdReporter;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.InboxManager;
import org.localstorm.mcc.ejb.gtd.dao.FiredHintsReportBean;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.util.SessionUtil;

import org.localstorm.mcc.web.RequestAttributes;

/**
 * @author localstorm
 */
public class GtdDashboardAgent {

    public static void prepare(ActionBeanContext context)
    {
        InboxManager im = ContextLookup.lookup(InboxManager.class, InboxManager.BEAN_NAME);
        GtdReporter rep = ContextLookup.lookup(GtdReporter.class,  GtdReporter.BEAN_NAME);
        HintManager hmb = ContextLookup.lookup(HintManager.class,  HintManager.BEAN_NAME);

        HttpServletRequest request = context.getRequest();

        User u = (User) SessionUtil.getValue(
                                      request.getSession(true),
                                      GtdSessionKeys.USER
                                    );

        DashboardReportBean  drb = rep.getDashboardReport(u);
        FiredHintsReportBean fhr = hmb.getFiredHintsReport(u);
        List<InboxEntry>    ibox = im.getInboxEntries(u);

        request.setAttribute(RequestAttributes.GTD_DASHBOARD_INBOX_BEAN,  ibox);
        request.setAttribute(RequestAttributes.GTD_DASHBOARD_REPORT_BEAN, drb);
        request.setAttribute(RequestAttributes.GTD_DASHBOARD_HINTS_REPORT_BEAN, fhr);
    }
    
}
