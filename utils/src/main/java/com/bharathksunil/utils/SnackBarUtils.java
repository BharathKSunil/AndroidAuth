package com.bharathksunil.utils;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * This Utility has methods related to snackBars, creating appTheme specific SnackBar, error SnackBar<br/>
 * <b>Note: </b> The accent Color must be set by the user in the Application Class by calling the
 * {@link #initialise(int)} method
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class SnackBarUtils {

    private static int accentColor = R.color.snackbar_default_accent;

    private SnackBarUtils() {
        //do not let anyone create an instance of this class
    }

    public static void initialise(@ColorRes @ColorInt int accentColor) {
        SnackBarUtils.accentColor = accentColor;
    }

    /**
     * Call this method to show a SnackBar with custom background color.<br/>
     * <b>Note: </b>The color of text should be selected such that the message is visible on the
     * SnackBar with the background color.
     *
     * @param rootView        the view on which the SnackBar must be displayed on
     * @param message         the message that must be displayed
     * @param duration        the duration of the message in milliseconds or use SnackBar.LENGTH_XXXX
     * @param backgroundColor the color of the background
     * @param textColor       the color of the text
     */
    public static void showCustomisedSnackBar(@NonNull View rootView, @NonNull final String message,
                                              int duration, int backgroundColor, int textColor) {
        Snackbar snackbar = Snackbar.make(rootView, message, duration);
        snackbar.getView().setBackgroundColor(backgroundColor);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }

    /**
     * Call this method to show a Normal SnackBar with black background and white text
     * on the view with a message and the duration of the message
     *
     * @param rootView the view on which the snack bar must be displayed
     * @param message  the message that must be displayed
     * @param duration the duration of the message in milliseconds or use SnackBar.LENGTH_XXXX
     */
    public static void showSimpleSnackBar(@NonNull View rootView, @NonNull final String message,
                                          int duration) {
        showCustomisedSnackBar(
                rootView,
                message,
                duration,
                Color.BLACK,
                Color.WHITE
        );
    }

    /**
     * This function makes apps accent colored SnackBar for displaying messages for Long interval
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the message to be shown
     */
    public static void showLongSnackBar(@NonNull final Activity activity, @NonNull final String message) {
        showCustomisedSnackBar(
                ((ViewGroup) activity.findViewById(android.R.id.content))
                        .getChildAt(0),//gets the root view of the activity
                message,
                Snackbar.LENGTH_LONG,
                ContextCompat.getColor(activity, accentColor),
                Color.WHITE
        );
    }

    /**
     * This function makes apps accent colored SnackBar for displaying messages for Long interval
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the string resource of message to be shown
     */
    public static void showLongSnackBar(@NonNull final Activity activity, @StringRes int message) {
        showLongSnackBar(activity, activity.getString(message));
    }

    /**
     * TThis function makes apps accent colored SnackBar for displaying messages for short interval
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the message to be shown
     */
    public static void showShortSnackBar(@NonNull final Activity activity, @NonNull final String message) {
        showCustomisedSnackBar(
                ((ViewGroup) activity.findViewById(android.R.id.content))
                        .getChildAt(0),//gets the root view of teh activity
                message,
                Snackbar.LENGTH_SHORT,
                ContextCompat.getColor(activity, accentColor),
                Color.WHITE
        );
    }

    /**
     * This function makes apps accent colored SnackBar for displaying messages for short interval
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the string resource of message to be shown
     */
    public static void showShortSnackBar(@NonNull final Activity activity, @StringRes int message) {
        showShortSnackBar(activity, activity.getString(message));
    }

    /**
     * This function makes a Red colored SnackBar for 4 seconds for displaying errors.
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the message to be shown
     */
    public static void showErrorBar(@NonNull final Activity activity, @NonNull final String message) {
        showCustomisedSnackBar(
                ((ViewGroup) activity.findViewById(android.R.id.content))
                        .getChildAt(0),//gets the root view of the activity
                message,
                4000,
                ContextCompat.getColor(activity, android.R.color.holo_red_dark),
                Color.WHITE
        );
    }

    /**
     * This function makes a Red colored SnackBar for 4 seconds for displaying errors.
     *
     * @param activity the activity on which the snack must be shown
     * @param message  the message to be shown
     */
    public static void showErrorBar(@NonNull final Activity activity, @StringRes int message) {
        showErrorBar(activity, activity.getString(message));
    }
}
