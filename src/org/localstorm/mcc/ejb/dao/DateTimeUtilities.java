package org.localstorm.mcc.ejb.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Incapsulates all common operations with <code>Date</code> objects.
 * Contains only <code>static</code> methods.  
 */
public final class DateTimeUtilities
{
    public static final TimeZone UTC_TIMEZONE                  = TimeZone.getTimeZone("UTC");
    public static final TimeZone LOCAL_TIMEZONE                = TimeZone.getDefault();
    public static final TimeZone FIRST_AREA_TIMEZONE           = TimeZone.getTimeZone("Europe/Moscow");
    public static final TimeZone SECOND_AREA_TIMEZONE          = TimeZone.getTimeZone("Asia/Krasnoyarsk");
    public static final String   MONTHS_TIMESTAMP_FORMAT       = "yyyyMM";
    public static final String   DAYS_TIMESTAMP_FORMAT         = "yyyyMMdd";
    public static final String   MINUTES_TIMESTAMP_FORMAT      = "yyyyMMddHHmm";
    public static final String   SECONDS_TIMESTAMP_FORMAT      = "yyyyMMddHHmmss";
    public static final String   MILLISECONDS_TIMESTAMP_FORMAT = "yyyyMMddHHmmss,SSS";
    public static final String   HOURS_AND_MINUTES_TIME_FORMAT = "HHmm";
    public static final String   DEFAULT_TIMESTAMP_FORMAT      = DateTimeUtilities.SECONDS_TIMESTAMP_FORMAT;

    public static final String   HUMAN_DATE_FORMAT             = "dd.MM.yyyy";
    public static final String   HUMAN_TIME_FORMAT             = "HH:mm";
    public static final String   HUMAN_TIMESTAMP_FORMAT        = DateTimeUtilities.HUMAN_TIME_FORMAT + " "
                                                                       + DateTimeUtilities.HUMAN_DATE_FORMAT;

    private DateTimeUtilities()
    {
    }

    public static TimeZone getTimeZone(String timezoneId)
    {
        // There may be real-time check, if specified timezoneId exists,
        //     because TimeZone.getTimeZone() do not return null,
        //     but return GMT timezone, if specified ID is not found,
        //     and this may be source of errors.
        return TimeZone.getTimeZone(timezoneId);
    }

    public static String timestampToString(final Date date)
    {
        return DateTimeUtilities.timestampToString(date, DateTimeUtilities.UTC_TIMEZONE);
    }

    public static String timestampToString(final Date date,
                                           final String format)
    {
        return DateTimeUtilities.timestampToString(date, DateTimeUtilities.UTC_TIMEZONE, format);
    }

    public static String timestampToString(final Date date,
                                           final TimeZone timezone)
    {
        return DateTimeUtilities.timestampToString(date, timezone, DateTimeUtilities.DEFAULT_TIMESTAMP_FORMAT);
    }

    public static String timestampToString(final Date date,
                                           final TimeZone timezone,
                                           final String format)
    {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setCalendar(Calendar.getInstance(timezone));
        return simpleDateFormat.format(date);
    }

    public static DateFormat getDateFormat(String formatStr,
                                           String timeZoneSystemName)
    {
        final SimpleDateFormat format = new SimpleDateFormat(formatStr);
        final TimeZone timeZone = DateTimeUtilities.getTimeZone(timeZoneSystemName);
        format.setTimeZone(timeZone);

        return format;
    }

    public static Date parseDate(final String dateStr)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, DateTimeUtilities.UTC_TIMEZONE);
    }

    public static Date parseDate(final String dateStr,
                                 final String format)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, DateTimeUtilities.UTC_TIMEZONE, format);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone)
        throws ParseException
    {
        return DateTimeUtilities.parseDate(dateStr, timezone, DateTimeUtilities.DEFAULT_TIMESTAMP_FORMAT);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 boolean isSummer)
        throws ParseException
    {
        final Date date = DateTimeUtilities.parseDate(dateStr, timezone);
        return DateTimeUtilities.updateDateWithSummerFlag(date, timezone, isSummer);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 final String format)
        throws ParseException
    {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setCalendar(Calendar.getInstance(timezone));
        return simpleDateFormat.parse(dateStr);
    }

    public static Date parseDate(final String dateStr,
                                 final TimeZone timezone,
                                 final String format,
                                 boolean isSummer)
        throws ParseException
    {
        final Date date = DateTimeUtilities.parseDate(dateStr, timezone, format);
        return DateTimeUtilities.updateDateWithSummerFlag(date, timezone, isSummer);
    }

    private static Date updateDateWithSummerFlag(Date date,
                                                 TimeZone timezone,
                                                 boolean isSummer)
    {
        if (timezone.useDaylightTime())
        {
            if (timezone.inDaylightTime(date) && !isSummer)
            {
                // Must correct summer time to winter time
                date.setTime(date.getTime() - timezone.getDSTSavings());
            } else if (!timezone.inDaylightTime(date) && isSummer)
            {
                // Must correct winter time to summer time
                date.setTime(date.getTime() + timezone.getDSTSavings());
            }
        }
        return date;
    }

    public static TimeZone getRUTimeZoneByUTCOffset(final int utcOffsetSecondsToEast)
    {
        final String timeZoneId = "UTC" + (utcOffsetSecondsToEast > 0 ? "+" : "") + utcOffsetSecondsToEast
                + "s_TIMEZONE_WITH_RU_DST_SCHEDULE";
        return new SimpleTimeZone(utcOffsetSecondsToEast * 1000, timeZoneId, Calendar.MARCH, -1, Calendar.SUNDAY,
            7200000, SimpleTimeZone.STANDARD_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 7200000,
            SimpleTimeZone.STANDARD_TIME, 3600000);
    }

    public static Calendar lastMidnightCalendar(TimeZone timezone)
    {
        if (timezone == null)
        {
            throw new NullPointerException("timezone is null");
        }

        final Calendar calendar = Calendar.getInstance(timezone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date lastMidnight(TimeZone timezone)
    {
        return DateTimeUtilities.lastMidnightCalendar(timezone).getTime();
    }

    public static Calendar lastLocalMidnightCalendar()
    {
        return DateTimeUtilities.lastMidnightCalendar(TimeZone.getDefault());
    }

    public static Date lastLocalMidnight()
    {
        return DateTimeUtilities.lastLocalMidnightCalendar().getTime();
    }

    public static Calendar lastUTCMidnightCalendar()
    {
        return DateTimeUtilities.lastMidnightCalendar(DateTimeUtilities.UTC_TIMEZONE);
    }

    public static Date lastUTCMidnight()
    {
        return DateTimeUtilities.lastUTCMidnightCalendar().getTime();
    }

    /**
     * Returns minimal date.
     * @param date1 Date.
     * @param date2 Date.
     * @return Minimal date.
     * @throws NullPointerException If <code>(date1 == null) || (date2 == null)</code>.
     */
    public static Date getMinDate(Date date1,
                                  Date date2)
    {
        if (date1 == null)
        {
            throw new NullPointerException("date1 parameter is null");
        }

        if (date2 == null)
        {
            throw new NullPointerException("date2 parameter is null");
        }

        return date1.after(date2) ? date2 : date1;
    }

    /**
     * Returns maximal date.
     * @param date1 Date.
     * @param date2 Date.
     * @return Maximal date.
     * @throws NullPointerException If <code>(date1 == null) || (date2 == null)</code>.
     */
    public static Date getMaxDate(Date date1,
                                  Date date2)
    {
        if (date1 == null)
        {
            throw new NullPointerException("date1 parameter is null");
        }

        if (date2 == null)
        {
            throw new NullPointerException("date2 parameter is null");
        }

        return date1.after(date2) ? date1 : date2;
    }

    public static void setLastMidnight(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Returns calendar with local time zone.
     * @return Calendar with local time zone.
     */
    public static Calendar getLocalCalendar()
    {
        return Calendar.getInstance(DateTimeUtilities.LOCAL_TIMEZONE);
    }

    /**
     * Returns calendar with UTC time zone.
     * @return Calendar with UTC time zone.
     */
    public static Calendar getUTCCalendar()
    {
        return Calendar.getInstance(DateTimeUtilities.UTC_TIMEZONE);
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Date}.
     * @return {@link java.sql.Date} object.
     */
    public static java.sql.Date getSqlDate(Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Time}.
     * @return {@link java.sql.Time} object.
     */
    public static java.sql.Time getSqlTime(Date date)
    {
        return new java.sql.Time(date.getTime());
    }

    /**
     * Converts {@link java.util.Date} to {@link java.sql.Timestamp}.
     * @return {@link java.sql.Timestamp} object.
     */
    public static java.sql.Timestamp getSqlTimestamp(Date date)
    {
        return new java.sql.Timestamp(date.getTime());
    }

    public static Date getDayBeginForDate(Date date,
                                          TimeZone timeZone)
    {
        final Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthBeginForDate(Date date,
                                            TimeZone timeZone)
    {
        final Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(DateTimeUtilities.getDayBeginForDate(date, timeZone));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

}
