package com.bharathksunil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * A Utility for maintaining uniformity od date time storage
 *
 * @author Bharath on 24-02-2018.
 */

public class DateUtil {
    private static final String DATE_PATTERN = "hh:mm:ss a, dd MMMM yyyy";

    public static String getCurrentDateTimeAsString() {
        String now;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));
        now = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(calendar.getTime());
        return now;
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    public static int getDateDiff(Date nowDate, Date oldDate, DateTimeUnits dateDiff) {
        long diffInMs = nowDate.getTime() - oldDate.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMs);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs)));
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        switch (dateDiff) {
            case DAYS:
                return days;
            case SECONDS:
                return seconds;
            case MINUTES:
                return minutes;
            case HOURS:
                return hours;
            case MILLISECONDS:
            default:
                return (int) diffInMs;
        }
    }

    public static boolean isTimePast(String time) {
        Date now, then;
        long diff;
        try {
            now = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(getCurrentDateTimeAsString());
            then = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(time);
            diff = getDateDiff(now, then, DateTimeUnits.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            diff = 0;
        }
        return diff > 0;
    }

    public static String getTimeDifferenceInMinutes(String first, String second) {
        long timeDifference;
        try {
            Date firstData = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(first);
            Date secondData = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(second);
            timeDifference = getDateDiff(secondData, firstData, DateTimeUnits.MINUTES);
        } catch (ParseException e) {
            timeDifference = 0;
        }
        return Long.toString(timeDifference);
    }

    public static Long getTimestampFromDate(String date) {
        Date thisDate;
        try {
            thisDate = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return thisDate.getTime();
    }

    enum DateTimeUnits {
        /**
         * Days
         */
        DAYS,
        /**
         * Hours
         */
        HOURS,
        /**
         * Minutes
         */
        MINUTES,
        /**
         * Seconds
         */
        SECONDS,
        /**
         * Milliseconds
         */
        MILLISECONDS,
    }
}

