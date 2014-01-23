package org.localstorm.mcc.ejb.gtd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.dao.Guard;
import org.localstorm.mcc.ejb.dao.JdbcDaoHelper;
import org.localstorm.mcc.ejb.dao.QueriesLoader;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public class HintReportsDao {

    private static final Logger log = Logger.getLogger(HintReportsDao.class);
    public static final String LIST_ID = "list_id";
    public static final String TASK_ID = "id";
    public static final String SUMMARY = "summary";

    private DataSource ds;

    public HintReportsDao(DataSource ds)
    {
        Guard.checkDataSourceNotNull(ds);
        this.ds = ds;
    }

    public FiredHintsReportBean getFiredHintsReport(User user) throws SQLException
    {
        QueriesLoader ql = QueriesLoader.getInstance();
        String    rptSql = ql.getQuery(QueriesLoader.FIRED_HINTS_REPORT);

        Connection conn = null;
        try {
            conn = ds.getConnection();
            Guard.checkConnectionNotNull(conn);

            PreparedStatement ps = conn.prepareStatement(rptSql);
            {
                JdbcDaoHelper.setInteger(ps, 1, user.getId());
            }

            ResultSet rs = ps.executeQuery();

            FiredHintsReportBean rpt = new FiredHintsReportBean();

            while (rs.next())
            {
                // Null is impossible here

                int listId     = JdbcDaoHelper.getInteger(rs, LIST_ID);
                int taskId     = JdbcDaoHelper.getInteger(rs, TASK_ID);
                String summary = JdbcDaoHelper.getString(rs,  SUMMARY);

                // Interpreting
                rpt.addFired(summary, taskId, listId);
            }

            return rpt;
        } finally {
            JdbcDaoHelper.safeClose(conn, log);
        }
    }

}
