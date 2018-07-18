package com.bharathksunil.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentTransactionUtils {

    /**
     * Call this method to load a fragment
     *
     * @param activity       the activity to which the fragment is loaded.
     * @param fragment       the fragment that must be loaded.
     * @param frame          the FrameLayout to which the fragment muse be loaded
     * @param tag            the unique tag that is given to identify the fragment
     * @param addToBackStack if the fragment must be added to the back stack
     */
    public static void loadFragment(@NonNull AppCompatActivity activity,
                                    @NonNull Fragment fragment,
                                    @IdRes int frame,
                                    @NonNull String tag,
                                    boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(frame, fragment, tag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }
}
