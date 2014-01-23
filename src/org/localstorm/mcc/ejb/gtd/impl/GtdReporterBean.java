package org.localstorm.mcc.ejb.gtd.impl;

import org.localstorm.mcc.ejb.gtd.dao.DashboardReportBean;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.gtd.dao.GtdReportsDao;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class GtdReporterBean implements GtdReporterLocal
{

    @Resource(mappedName=Constants.DEFAULT_DS)
    private DataSource ds;

    @Override
    public DashboardReportBean getDashboardReport(User user) {

        GtdReportsDao grd = new GtdReportsDao(ds);
        
        try {
            return grd.getDashboardReport(user);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
