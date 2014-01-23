package org.localstorm.mcc.ejb.people.impl;

import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.people.dao.DashboardReportBean;
import org.localstorm.mcc.ejb.people.dao.PeopleReportsDao;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class PeopleReporterBean extends PeopleStatelessBean implements PeopleReporterLocal
{

    @Resource(mappedName=Constants.DEFAULT_DS)
    private DataSource ds;

    @Override
    public DashboardReportBean getDashboardReport(User user) {

        PeopleReportsDao grd = new PeopleReportsDao(ds);

        try {
            return grd.getDashboardReport(user);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
   
}
