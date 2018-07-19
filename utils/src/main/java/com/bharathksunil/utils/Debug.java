package com.bharathksunil.utils;

import android.util.Log;

/**
 * <h>Debug</h>
 * THIS IS A HOUSE KEEPING CLASS WHICH HELPS IN SENDING LOG MESSAGES, TOASTS AND simple SNACK BAR
 *
 * @author Bharath Kumar S
 * @since 04-01-2017
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Debug {
    private Debug() {

    }

    /**
     * THIS METHOD PRINTS THE ERROR MESSAGE TO THE LOGGER
     * FORMAT: Exception Occurred in:  <class_name>.<method_name> : error message
     *
     * @param message THIS GIVES THE CLASS_NAME.METHOD_NAME AT WHICH THE ERROR OCCURRED AND THE ERROR MESSAGE
     */
    public static void e(String message) {
        Log.e("BKS", "Exception Occurred in: " + message);
    }

    /**
     * THIS METHOD IS USED TO ANALISE THE EXECUTION, IT CAN BE USED TO PRINT VALUE OF ANY OBJECT AND IS HELP_FULL IN ANALYSING AND DEBUGGING
     *
     * @param message THIS IS THE MESSAGE VIZ TO BE PRINTED
     */
    public static void i(String message) {
        Log.i("BKS", message);
    }


}
