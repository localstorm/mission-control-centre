package org.localstorm.mcc.ejb.people;

import org.localstorm.mcc.ejb.people.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
public interface PeopleReporter {
    public static final String BEAN_NAME = "PeopleReporterBean";
    
    public DashboardReportBean getDashboardReport(User user);
}
