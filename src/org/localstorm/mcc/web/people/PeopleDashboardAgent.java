package org.localstorm.mcc.web.people;

import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.people.PeopleReporter;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.util.SessionUtil;

import org.localstorm.mcc.web.RequestAttributes;

/**
 * @author localstorm
 */
public class PeopleDashboardAgent {

    public static void prepare(ActionBeanContext context)
    {
        PeopleReporter rep = ContextLookup.lookup(PeopleReporter.class, PeopleReporter.BEAN_NAME);
        HttpServletRequest request = context.getRequest();

        User u = (User) SessionUtil.getValue(
                                      request.getSession(true),
                                      PeopleSessionKeys.USER
                                    );

        DashboardReportBean drb = rep.getDashboardReport(u);
        request.setAttribute(RequestAttributes.PPL_DASHBOARD_REPORT_BEAN, drb);
    }
    
}
