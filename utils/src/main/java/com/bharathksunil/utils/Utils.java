package com.bharathksunil.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * <h>Utils</h>
 * This is a Java Class Which provides many generic utilities for functioning of the app
 * <br/>
 *
 * @author Bharath <email>bharathk.sunil.k@gmail.com</email>
 */
@SuppressWarnings({"unused", "WeakerAccess", "squid:S2209", "squid:S2440"})
public class Utils {

    private static final Debug sDebug = new Debug(Utils.class.getSimpleName());

    private Utils() {

    }

    public static final int PERMISSION_REQUEST_CODE = 22;

    /**
     * This function is used to check if the App is connected to the Internet
     * <b>NOTE:</b> The App Must have a Permission :
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *
     * @return true if the app is connected to the internet
     */
    public static boolean isConnectedToTheNetwork(@NonNull Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo nInfo = cManager != null ? cManager.getActiveNetworkInfo() : null;
        //IF THE NETWORK IS AVAILABLE:
        return (nInfo != null && nInfo.isConnected());
    }

    public static boolean isPermissionGranted(@NonNull final Context context,
                                              @NonNull String permissionString) {
        return Build.VERSION.SDK_INT < 23 || context.checkSelfPermission(permissionString)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * this function is used for hide keyboard panel.
     *
     * @param activity - activity instance
     */
    @SuppressWarnings("ConstantConditions")
    public static void hideKeyboard(@NonNull final Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            sDebug.e("Utils.hideKeyboard" + e.getMessage());
        }
    }

    public static boolean isHighAccuracyLocationEnabled(@NonNull Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
