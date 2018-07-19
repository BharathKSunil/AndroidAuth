package com.bharathksunil.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * <h>Utils</h>
 * This is a Java Class Which provides many generic utilities for functioning of the app
 * <br/>
 *
 * @author Bharath <email>bharathk.sunil.k@gmail.com</email>
 */
public class Utils {

    public static final int PERMISSION_REQUEST_CODE = 22;

    /**
     * This function is used to check if the App is connected to the Internet
     * <b>NOTE:</b> The App Must have a Permission :
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *
     * @return true if the app is connected to the internet
     */
    public static boolean isConnectedToTheNetwork(@NonNull Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager != null ? cManager.getActiveNetworkInfo() : null;
        //IF THE NETWORK IS AVAILABLE:
        return (nInfo != null && nInfo.isConnected());
    }

    public static boolean isPermissionGranted(Activity activity, @NonNull String permissionString) {
        return Build.VERSION.SDK_INT < 23 || activity.checkSelfPermission(permissionString)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * this function is used for hide keyboard panel.
     *
     * @param activity - activity instance
     */
    @SuppressWarnings("ConstantConditions")
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Debug.e("Utils.hideKeyboard" + e.getMessage());
        }
    }
    /*public static boolean isHighAccuracyLocationEnabled(@NonNull Context context) { int locationMode;
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
*/
}
