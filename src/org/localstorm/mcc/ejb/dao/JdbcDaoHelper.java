package org.localstorm.mcc.ejb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;


/**
 * Used to set object values into database (including null values).
 */
public final class JdbcDaoHelper
{
    /** SQLException code for ORA-02291 */
    public static final int     SQL_CONSTRAINT_VIOLATED = 2291;

    private static final String DB_VALIDATION_QUERY     = "SELECT COUNT(*) FROM DUAL";

    private JdbcDaoHelper()
    {
    }

    public static String getFieldWhereString(String fieldName,
                                             Object fieldValue)
    {
        assert (fieldName != null);

        if (fieldValue == null)
        {
            return "(" + fieldName + " is null)";
        } else
        {
            return "(" + fieldName + " = ?)";
        }
    }

    public static void setInteger(PreparedStatement stmt,
                                  int index,
                                  Integer param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.INTEGER);
        } else
        {
            stmt.setInt(index, param);
        }
    }

    public static void setInteger(PreparedStatement stmt,
                                  int index,
                                  int param)
        throws SQLException
    {
        assert (stmt != null && index > 0);
        stmt.setInt(index, param);
    }

    public static Integer getInteger(ResultSet rs,
                                     int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        Integer value = rs.getInt(index);

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static Integer getInteger(ResultSet rs,
                                     String colName)
        throws SQLException
    {
        assert (rs != null && colName != null && colName.length() > 0);
        Integer value = rs.getInt(colName);

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static void setLong(PreparedStatement stmt,
                               int index,
                               Long param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.BIGINT);
        } else
        {
            stmt.setLong(index, param);
        }
    }

    public static void setLong(PreparedStatement stmt,
                               int index,
                               long param)
        throws SQLException
    {
        assert (stmt != null && index > 0);
        stmt.setLong(index, param);
    }

    public static Long getLong(ResultSet rs,
                               int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        Long value = rs.getLong(index);

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static Long getLong(ResultSet rs,
                               String colName)
        throws SQLException
    {
        assert (rs != null && colName != null && colName.length() > 0);
        Long value = rs.getLong(colName);

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static void setDouble(PreparedStatement stmt,
                                 int index,
                                 Double param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.DOUBLE);
        } else if (param.isNaN() || param.isInfinite())
        {
            throw new IllegalArgumentException("NaN and infinite values can't be saved to database (" + param + ")");
        } else
        {
            stmt.setDouble(index, param);
        }
    }

    public static void setDouble(PreparedStatement stmt,
                                 int index,
                                 double param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (Double.isNaN(param) || Double.isInfinite(param))
        {
            throw new IllegalArgumentException("NaN and infinite values can't be saved to database (" + param + ")");
        }

        stmt.setDouble(index, param);
    }

    public static Double getDouble(ResultSet rs,
                                   int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        Double value = rs.getDouble(index);

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static void setFloat(PreparedStatement stmt,
                                int index,
                                Float param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.FLOAT);
        } else if (param.isNaN() || param.isInfinite())
        {
            throw new IllegalArgumentException("NaN and infinitive can't be saved to database (" + param + ")");
        } else
        {
            stmt.setFloat(index, param);
        }
    }

    public static void setFloat(PreparedStatement stmt,
                                int index,
                                float param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (Float.isNaN(param) || Float.isInfinite(param))
        {
            throw new IllegalArgumentException("NaN and infinite values can't be saved to database (" + param + ")");
        }

        stmt.setFloat(index, param);
    }

    public static Float getFloat(ResultSet rs,
                                 int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        Float value = rs.getFloat(index);
        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static Float getFloat(ResultSet rs,
                                 String columnName)
        throws SQLException
    {
        assert (rs != null && columnName != null && columnName.length() > 0);
        Float value = rs.getFloat(columnName);
        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static void setNumber(PreparedStatement stmt,
                                 int index,
                                 Number param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.DOUBLE);
        } else
        {
            stmt.setDouble(index, param.doubleValue());
        }
    }

    public static void setBoolean(PreparedStatement stmt,
                                  int index,
                                  Boolean param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.BIT);
        } else
        {
            stmt.setBoolean(index, param);
        }
    }

    public static void setBoolean(PreparedStatement stmt,
                                  int index,
                                  boolean param)
        throws SQLException
    {
        assert (stmt != null && index > 0);
        stmt.setBoolean(index, param);
    }

    public static Boolean getBoolean(ResultSet rs,
                                     int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        Boolean value = rs.getBoolean(index) ? Boolean.TRUE : Boolean.FALSE;

        if (rs.wasNull())
        {
            value = null;
        }

        return value;
    }

    public static void setDate(PreparedStatement stmt,
                               int index,
                               Date param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.DATE);
        } else
        {
            final java.sql.Date date = DateTimeUtilities.getSqlDate(param);
            stmt.setDate(index, date, DateTimeUtilities.getUTCCalendar());
        }
    }

    public static Date getDate(ResultSet rs,
                               int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        return rs.getDate(index, DateTimeUtilities.getUTCCalendar());
    }

    public static Date getDate(ResultSet rs, String colName)
        throws SQLException
    {
        assert (rs != null && colName != null && colName.length() > 0);
        return rs.getDate(colName, DateTimeUtilities.getUTCCalendar());
    }


    public static void setTime(PreparedStatement stmt,
                               int index,
                               Date param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.TIME);
        } else
        {
            final java.sql.Time time = DateTimeUtilities.getSqlTime(param);
            stmt.setTime(index, time, DateTimeUtilities.getUTCCalendar());
        }
    }

    public static Date getTime(ResultSet rs,
                               int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        return rs.getTime(index, DateTimeUtilities.getUTCCalendar());
    }

    public static Date getTime(ResultSet rs,
                               String colName)
        throws SQLException
    {
        assert (rs != null && colName != null && colName.length() > 0);
        return rs.getTime(colName, DateTimeUtilities.getUTCCalendar());
    }

    public static void setTimestamp(PreparedStatement stmt,
                                    int index,
                                    Date param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.TIMESTAMP);
        } else
        {
            final java.sql.Timestamp timestamp = DateTimeUtilities.getSqlTimestamp(param);
            stmt.setTimestamp(index, timestamp, DateTimeUtilities.getUTCCalendar());
        }
    }

    public static Date getTimestamp(ResultSet rs,
                                    int index)
        throws SQLException
    {
        // Made to prevent bugs with special treatment of Timestamp.equals() method.
        assert (rs != null && index > 0);
        return new Date(rs.getTimestamp(index, DateTimeUtilities.getUTCCalendar()).getTime());
    }

    public static Date getTimestamp(ResultSet rs,
                                    String colName)
        throws SQLException
    {
        // Made to prevent bugs with special treatment of Timestamp.equals() method.
        assert (rs != null && colName != null && colName.length() > 0);
        return new Date(rs.getTimestamp(colName, DateTimeUtilities.getUTCCalendar()).getTime());
    }

    public static void setString(PreparedStatement stmt,
                                 int index,
                                 String param)
        throws SQLException
    {
        assert (stmt != null && index > 0);

        if (param == null)
        {
            stmt.setNull(index, Types.VARCHAR);
        } else
        {
            stmt.setString(index, param);
        }
    }

    public static String getString(ResultSet rs,
                                   int index)
        throws SQLException
    {
        assert (rs != null && index > 0);
        return rs.getString(index);
    }

    public static String getString(ResultSet rs,
                                   String colName)
        throws SQLException
    {
        assert (rs != null && colName != null && colName.length() > 0);
        return rs.getString(colName);
    }

    public static void safeClose(Connection c,
                                 Logger log)
    {
        if (c == null)
        {
            return;
        }
        try
        {
            if (!c.isClosed())
            {
                c.close();
            }
        } catch (final SQLException sqle)
        {
            if (null != log)
            {
                log.warn("Exception occured on connection closing", sqle);
            }
        }
    }

    public static void safeClose(ResultSet rs,
                                 Logger log)
    {
        if (rs == null)
        {
            return;
        }
        try
        {
            rs.close();
        } catch (final SQLException sqle)
        {
            if (null != log)
            {
                log.warn("Exception occured on resultset closing", sqle);
            }
        }
    }

    public static void safeClose(Statement stmt,
                                 Logger log)
    {
        if (stmt == null)
        {
            return;
        }
        try
        {
            stmt.close();
        } catch (final SQLException sqle)
        {
            if (null != log)
            {
                log.warn("Exception occured on statement closing", sqle);
            }
        }
    }

}
