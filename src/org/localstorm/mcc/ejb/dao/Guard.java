package org.localstorm.mcc.ejb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author localstorm
 */
public class Guard {
    
    public static void checkConnectionNotNull(Connection conn) throws SQLException {
        if (conn == null) {
            throw new SQLException("Can't obtain a connection.");
        }
    }

    public static void checkDataSourceNotNull(DataSource ds) {
        if (ds==null) {
            throw new NullPointerException("Given DataSource is null!");
        }
    }

    public static void checkNotNull(Object o) {
        if (o==null) {
            throw new NullPointerException("Given object is null!");
        }
    }
}
