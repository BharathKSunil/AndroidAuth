package com.bharathksunil.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.bharathksunil.utils.DateUtil.DateTimeUnits.MINUTES;

/**
 * A Utility for maintaining uniformity of date time storage
 *
 * @author Bharath on 24-02-2018.
 */
@SuppressWarnings({"unused", "WeakerAccess", "squid:S2209", "squid:S2440"})
public class DateUtil {
    private static String datePattern = "hh:mm:ss a, dd MMMM yyyy";
    private static String timeZone = "IST";
    private static final Debug sDebug = new Debug(DateUtil.class.getSimpleName());

    /**
     * Initialise this in the Apps Application class's onCreate() and pass a valid date pattern you
     * would like to use across the app.
     * By default we will be using {@link #datePattern} which gives: 12:35:59 AM, 12 March 2018
     *
     * @param datePattern a valid java date pattern.
     */
    public static void setDatePattern(@NonNull final String datePattern) {
        DateUtil.datePattern = datePattern;
    }

    /**
     * Initialise this in the Apps Application class's onCreate() and pass a valid timeZone.
     * If not set, by default IST will be used.
     *
     * @param timeZone the timeZone that must be used for the app
     */
    public static void setTimeZone(@NonNull final String timeZone) {
        DateUtil.timeZone = timeZone;
    }

    /**
     * Call this method to get the date as the string in the pattern set in {@link #datePattern}
     *
     * @param date the date object
     * @return the formatted string of the date in the {@link #datePattern}
     */
    public static String getDateAsString(@NonNull final Date date) {
        return new SimpleDateFormat(datePattern, Locale.getDefault()).format(date);
    }

    /**
     * Call this method to get the date as a {@link Date} object from the string date
     *
     * @param date the date in the format of the string
     * @return date object corresponding to the date sting
     * @throws ParseException exception that occurred while passing the string as it is not a date.
     */
    public static Date getDateFromString(@NonNull final String date) throws ParseException {
        return new SimpleDateFormat(datePattern, Locale.getDefault()).parse(date);
    }

    /**
     * Call this method to get the current time and date as a string
     *
     * @return the current date and time.
     */
    @NonNull
    public static String getCurrentDateTimeAsString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        return getDateAsString(calendar.getTime());
    }

    /**
     * Get absolute difference between 2 times in the time unit you need.<br/>
     * <b>How to use:</b> Pass in the two dates you want the difference with the unit of difference.
     * eg: number of seconds/minutes/days between timeOne and timeTwo.
     *
     * @param timeOne   the first Date
     * @param timeTwo   the second Date
     * @param timeUnits the {@link DateTimeUnits} in which the difference is required
     * @return Difference(timeOne - timeTwo) between the two dates in the unit which was requested
     */
    public static String getAbsoluteTimeDifference(@NonNull final Date timeOne,
                                                   @NonNull final Date timeTwo,
                                                   @NonNull final DateTimeUnits timeUnits) {
        long diffInMs = Math.abs(timeOne.getTime() - timeTwo.getTime());
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMs);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs)));
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        switch (timeUnits) {
            case DAYS:
                return Long.toString(days);
            case SECONDS:
                return Long.toString(seconds);
            case MINUTES:
                return Long.toString(minutes);
            case HOURS:
                return Long.toString(hours);
            case MILLISECONDS:
            default:
                return Long.toString(diffInMs);
        }
    }

    /**
     * Get absolute difference between 2 times in the time unit you need.<br/>
     * <b>How to use:</b> Pass in the two dates you want the difference with the unit of difference.
     * eg: number of seconds/minutes/days between timeOne and timeTwo.
     *
     * @param timeOne   the first Date
     * @param timeTwo   the second Date
     * @param timeUnits the {@link DateTimeUnits} in which the difference is required
     * @return Difference(timeOne - timeTwo) between the two dates in the unit which was requested
     */
    @NonNull
    public static String getAbsoluteTimeDifference(@NonNull final String timeOne,
                                                   @NonNull final String timeTwo,
                                                   @NonNull final DateTimeUnits timeUnits) {
        try {
            Date firstDate = getDateFromString(timeOne);
            Date secondDate = getDateFromString(timeTwo);
            return getAbsoluteTimeDifference(secondDate, firstDate, timeUnits);
        } catch (ParseException e) {
            sDebug.e(DateUtil.class.getSimpleName() + ".getTimeDifferenceInMinutes(): " + e.getMessage());
        }
        return "NA";
    }

    /**
     * Get difference between 2 times in the time unit you need.<br/>
     * <b>How to use:</b> Pass in the two dates you want the difference with the unit of difference.
     * eg: number of seconds/minutes/days between timeOne and timeTwo.
     *
     * @param timeOne   the first Date
     * @param timeTwo   the second Date
     * @param timeUnits the {@link DateTimeUnits} in which the difference is required
     * @return Difference(timeOne - timeTwo) between the two dates in the unit which was requested
     */
    public static String getTimeDifference(@NonNull final Date timeOne,
                                           @NonNull final Date timeTwo,
                                           @NonNull final DateTimeUnits timeUnits) {
        long diffInMs = timeOne.getTime() - timeTwo.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMs);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs)));
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        switch (timeUnits) {
            case DAYS:
                return Long.toString(days);
            case SECONDS:
                return Long.toString(seconds);
            case MINUTES:
                return Long.toString(minutes);
            case HOURS:
                return Long.toString(hours);
            case MILLISECONDS:
            default:
                return Long.toString(diffInMs);
        }
    }

    /**
     * Get difference between 2 times in the time unit you need.<br/>
     * <b>How to use:</b> Pass in the two dates you want the difference with the unit of difference.
     * eg: number of seconds/minutes/days between timeOne and timeTwo.
     *
     * @param timeOne   the first Date
     * @param timeTwo   the second Date
     * @param timeUnits the {@link DateTimeUnits} in which the difference is required
     * @return Difference(timeOne - timeTwo) between the two dates in the unit which was requested
     */
    public static String getTimeDifference(@NonNull final String timeOne,
                                           @NonNull final String timeTwo,
                                           @NonNull final DateTimeUnits timeUnits) {
        try {
            Date firstDate = getDateFromString(timeOne);
            Date secondDate = getDateFromString(timeTwo);
            return getTimeDifference(secondDate, firstDate, timeUnits);
        } catch (ParseException e) {
            sDebug.e(DateUtil.class.getSimpleName() + ".getTimeDifferenceInMinutes(): " + e.getMessage());
        }
        return "NA";
    }

    /**
     * Call this function to check if the time has past the currentTime
     *
     * @param time the time you want to compare with the present
     * @return true if the time has passed the currentTime
     */
    public static boolean isTimePast(@NonNull final String time) {
        long diff = 0;
        try {
            diff = Long.parseLong(getTimeDifference(
                    getDateFromString(getCurrentDateTimeAsString()),
                    getDateFromString(time),
                    MINUTES
            ));
        } catch (ParseException e) {
            sDebug.e(DateUtil.class.getSimpleName() + ".isTimePast(): " + e.getMessage());
        }
        return diff > 0;
    }

    /**
     * Call this function to get the milliseconds that has passed since the epoch from the date string
     *
     * @param date the date string
     * @return the milliseconds since epoch
     */
    @Nullable
    public static Long getTimestampFromDate(@NonNull final String date) {
        Date thisDate;
        try {
            thisDate = getDateFromString(date);
        } catch (ParseException e) {
            sDebug.e(DateUtil.class.getSimpleName() + ".getTimestampFromDate(): " + e.getMessage());
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

