package com.bharathksunil.util;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bharathksunil.androidauthmvp.R;

/**
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class FragmentTransactionUtils {

    private FragmentTransactionUtils() {

    }

    //region Replace Fragment Methods

    /**
     * Call this method from the activity to replace a frame with the fragment passed. If you don't
     * want to give any animations for the transition then pass -1 or call
     * {@link #replaceFragment(FragmentManager, Fragment, int, String, boolean)}
     *
     * @param supportFragmentManager the Support Fragment Manager
     * @param fragment               the fragment to be replaced
     * @param frame                  the frame in which the fragment must be replaced
     * @param tag                    the unique tag for the Fragment
     * @param addToBackStack         if the fragment must be added to be back stack
     * @param entryAnimationRes      the entry animation resource
     * @param exitAnimationRes       the exit animation resource
     */
    public static void replaceFragment(@NonNull final FragmentManager supportFragmentManager,
                                       @NonNull final Fragment fragment, @IdRes int frame,
                                       @NonNull final String tag, boolean addToBackStack,
                                       @AnimRes int entryAnimationRes, @AnimRes int exitAnimationRes) {
        // This check is added to ensure we don't add the same fragment twice while the fragment is already added
        if (fragment.isAdded())
            return;
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        //check if the user does not want any animation
        if (!(entryAnimationRes == -1 || exitAnimationRes == -1))
            transaction.setCustomAnimations(
                    entryAnimationRes,
                    exitAnimationRes
            );

        transaction.replace(frame, fragment, tag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * Call this method from the activity to replace a frame with the fragment passed and don't want
     * to use any animation.
     *
     * @param supportFragmentManager the Support Fragment Manager
     * @param fragment               the fragment to be replaced
     * @param frame                  the frame in which the fragment must be replaced
     * @param tag                    the unique tag for the Fragment
     * @param addToBackStack         if the fragment must be added to be back stack
     */
    public static void replaceFragment(@NonNull final FragmentManager supportFragmentManager,
                                       @NonNull final Fragment fragment, @IdRes int frame,
                                       @NonNull final String tag, boolean addToBackStack) {
        replaceFragment(
                supportFragmentManager,
                fragment,
                frame,
                tag,
                addToBackStack,
                -1,
                -1
        );
    }

    /**
     * Call this method from the activity to replace a frame with the fragment passed and give it
     * some animation.
     *
     * @param supportFragmentManager the support fragment manager.
     * @param fragment               the fragment that must be loaded.
     * @param frame                  the FrameLayout to which the fragment muse be loaded
     * @param tag                    the unique tag that is given to identify the fragment
     * @param addToBackStack         if the fragment must be added to the back stack
     * @param transitionAnimation    the type of animation
     */
    public static void replaceFragment(@NonNull final FragmentManager supportFragmentManager,
                                       @NonNull final Fragment fragment, @IdRes int frame,
                                       @NonNull final String tag, boolean addToBackStack,
                                       @NonNull TransitionAnimation transitionAnimation) {
        int inAnim = android.R.anim.slide_in_left;
        int outAnim = android.R.anim.slide_out_right;
        switch (transitionAnimation) {
            case FADING:
                inAnim = R.anim.fade_in;
                outAnim = R.anim.fade_out;
                break;
            case SLIDING_IN_LEFT:
                inAnim = R.anim.slide_in_left;
                outAnim = R.anim.slide_out_right;
                break;
            case SLIDING_IN_RIGHT:
                inAnim = R.anim.slide_in_right;
                outAnim = R.anim.slide_out_left;
                break;
        }
        replaceFragment(
                supportFragmentManager,
                fragment,
                frame,
                tag,
                addToBackStack,
                inAnim,
                outAnim
        );

    }
    //endregion

    //region Add Fragment Methods

    /**
     * Call this method from the activity to add the fragment passed to a frame. If you don't
     * want to give any animations for the transition then pass -1 or call
     * {@link #replaceFragment(FragmentManager, Fragment, int, String, boolean)}
     *
     * @param supportFragmentManager the Support Fragment Manager
     * @param fragment               the fragment to be replaced
     * @param frame                  the frame in which the fragment must be replaced
     * @param tag                    the unique tag for the Fragment
     * @param addToBackStack         if the fragment must be added to be back stack
     * @param entryAnimationRes      the entry animation resource
     * @param exitAnimationRes       the exit animation resource
     */
    public static void addFragment(@NonNull final FragmentManager supportFragmentManager,
                                   @NonNull final Fragment fragment, @IdRes int frame,
                                   @NonNull final String tag, boolean addToBackStack,
                                   @AnimRes int entryAnimationRes, @AnimRes int exitAnimationRes) {
        // This check is added to ensure we don't add the same fragment twice while the fragment is already added
        if (fragment.isAdded())
            return;
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        //check if the user does not want any animation
        if (!(entryAnimationRes == -1 || exitAnimationRes == -1))
            transaction.setCustomAnimations(
                    entryAnimationRes,
                    exitAnimationRes
            );

        transaction.add(frame, fragment, tag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * Call this method from the activity to add the fragment passed to a frame and don't want
     * to use any animation.
     *
     * @param supportFragmentManager the Support Fragment Manager
     * @param fragment               the fragment to be replaced
     * @param frame                  the frame in which the fragment must be replaced
     * @param tag                    the unique tag for the Fragment
     * @param addToBackStack         if the fragment must be added to be back stack
     */
    public static void addFragment(@NonNull final FragmentManager supportFragmentManager,
                                   @NonNull final Fragment fragment, @IdRes int frame,
                                   @NonNull final String tag, boolean addToBackStack) {
        addFragment(
                supportFragmentManager,
                fragment,
                frame,
                tag,
                addToBackStack,
                -1,
                -1
        );
    }

    /**
     * Call this method from the activity to add the fragment passed to a frame and give it
     * some animation.
     *
     * @param supportFragmentManager the support fragment manager.
     * @param fragment               the fragment that must be loaded.
     * @param frame                  the FrameLayout to which the fragment muse be loaded
     * @param tag                    the unique tag that is given to identify the fragment
     * @param addToBackStack         if the fragment must be added to the back stack
     * @param transitionAnimation    the type of animation
     */
    public static void addFragment(@NonNull final FragmentManager supportFragmentManager,
                                   @NonNull final Fragment fragment, @IdRes int frame,
                                   @NonNull final String tag, boolean addToBackStack,
                                   @NonNull TransitionAnimation transitionAnimation) {
        int inAnim = android.R.anim.slide_in_left;
        int outAnim = android.R.anim.slide_out_right;
        switch (transitionAnimation) {
            case FADING:
                inAnim = R.anim.fade_in;
                outAnim = R.anim.fade_out;
                break;
            case SLIDING_IN_LEFT:
                inAnim = R.anim.slide_in_left;
                outAnim = R.anim.slide_out_right;
                break;
            case SLIDING_IN_RIGHT:
                inAnim = R.anim.slide_in_right;
                outAnim = R.anim.slide_out_left;
                break;
        }
        addFragment(
                supportFragmentManager,
                fragment,
                frame,
                tag,
                addToBackStack,
                inAnim,
                outAnim
        );

    }
    //endregion

    //region Remove Fragment Methods

    /**
     * Call this function to remove a fragment from the Activity
     *
     * @param fragmentManager the support fragment Manager
     * @param fragment        the fragment that must be removed
     */
    public static void removeFragment(@NonNull final FragmentManager fragmentManager,
                                      @NonNull final Fragment fragment) {
        if (fragment.isAdded() && !fragment.isRemoving())
            fragmentManager.beginTransaction().remove(fragment).commit();
    }

    /**
     * Call this method to remove the topmost fragment in the view stack
     *
     * @param fragmentManager the Support fragment manager
     */
    public static void removeCurrentFragment(@NonNull final FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }
    //endregion

    public enum TransitionAnimation {
        SLIDING_IN_LEFT,
        SLIDING_IN_RIGHT,
        FADING
    }
}
