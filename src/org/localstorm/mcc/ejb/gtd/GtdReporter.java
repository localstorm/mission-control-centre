package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.gtd.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
public interface GtdReporter {
    public static final String BEAN_NAME = "GtdReporterBean";
    
    public DashboardReportBean getDashboardReport(User user);
}
