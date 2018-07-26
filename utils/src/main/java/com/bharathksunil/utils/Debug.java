package com.bharathksunil.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * This is a useful class that helps in simpler log calls
 *
 * @author Bharath Kumar S
 * @since 04-01-2017
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Debug {
    private final String className;
    private static boolean isRelease;

    public Debug(@NonNull final String className) {
        this.className = className;
    }

    /**
     * Call this method to set if the log messages need to be printed in the release version.
     * Call this in the onCreate() method of your Application Class.
     *
     * @param isRelease true if its a release build
     */
    public static void setAsRelease(boolean isRelease) {
        Debug.isRelease = isRelease;
    }

    /**
     * To send an Error log messages
     *
     * @param message the message
     */
    public void e(String message) {
        Log.e(className, message);
    }

    /**
     * To send an Information log message
     *
     * @param message the message
     */
    public void i(String message) {
        if (!isRelease)
            Log.i(className, message);
    }

    /**
     * To send Debug log message
     *
     * @param message the message
     */
    public void d(String message) {
        if (!isRelease)
            Log.d(className, message);
    }

    /**
     * To send Warning log messages
     *
     * @param message the message
     */
    public void w(String message) {
        if (!isRelease)
            Log.w(className, message);
    }


}
