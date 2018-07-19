package com.bharathksunil.utils;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import java.util.List;

/**
 * @author Bharath Kumar S on 08-01-2018.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class ViewUtils {

    private ViewUtils() {
        //so that no instance is made
    }


    /**
     * Call this method to focus on any view inside the scrollView
     *
     * @param view       the view that needs to be focused on
     * @param scrollView the scrollview inside which the view is present
     */
    public static void focusOnView(@NonNull final View view, final @NonNull ScrollView scrollView) {
        new Handler().post(() -> {
            try {
                int vLeft = view.getLeft();
                int vRight = view.getRight();
                int sWidth = scrollView.getWidth();
                scrollView.smoothScrollTo(((vLeft + vRight - sWidth) / 2), view.getTop());
            } catch (Exception e) {
                //defensive programming, suppress the error
                Debug.e(" Utils.focusOnView : " + e.getMessage());
            }
        });
    }

    /**
     * This is used to make any activity a popup type with 75% background transparency. <br/>
     * <b>NOTE:</b> the activity must be having a theme with these properties:<br/>
     * <ul>
     * <li><item name="android:windowIsTranslucent">true</item></li>
     * <li><item name="android:windowCloseOnTouchOutside">true</item></li>
     * <li><item name="android:windowIsFloating">true</item></li>
     * </ul><br/>
     * <h4> How to use:</h4><br/>
     * Call this method at the onCreate of the activity that you want to make it as a popup
     * Pass the window of the activity. you may get the activity window by using getWindow().
     *
     * @param window the window corresponding to the activity that must be made as a popup.
     */
    public static void makePopupDisplay(@NonNull final Window window, float transparencyPercentage) {
        if (transparencyPercentage < 10.00f || transparencyPercentage > 100.00f)
            //Aggressive programming. notify the Developer that the value passed is invalid
            throw new IllegalArgumentException("The transparency amount passed must be between" +
                    "10.00 and 100.00. But the value passed is : " + transparencyPercentage);
        try {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = transparencyPercentage / 100.0f; //convert from percent to a value
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            //defensive programming, suppress the error
            Debug.e("Utils.makePopupDisplay(): " + e.getMessage());
        }
    }

    //region View Visibility Related Methods (Visible, Invisible, Gone)

    /**
     * Call this function to set the views as visible
     *
     * @param views the multiple views to set visible
     */
    public static void setVisible(View... views) {
        for (View v : views) v.setVisibility(View.VISIBLE);
    }

    /**
     * Call this function to set the views as visible, use this when the views are in a list
     * preferable to use this when using ButterKnife.
     *
     * @param views the multiple views to set visible
     */
    public static void setVisible(List<View> views) {
        for (View v : views) setVisible(v);
    }

    /**
     * Call this function to set the views as invisible
     *
     * @param views the multiple views to set invisible
     */
    public static void setInvisible(View... views) {
        for (View v : views) v.setVisibility(View.INVISIBLE);
    }

    /**
     * Call this function to set the views as invisible, use this when the views are in a list
     * preferable to use this when using ButterKnife.
     *
     * @param views the multiple views to set invisible
     */
    public static void setInvisible(List<View> views) {
        for (View v : views) setInvisible(v);
    }

    /**
     * Call this function to set the views as gone
     *
     * @param views the multiple views to set gone
     */
    public static void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * Call this function to set the views as gone, use this when the views are in a list
     * preferable to use this when using ButterKnife.
     *
     * @param views the multiple views to set gone
     */
    public static void setGone(List<View> views) {
        for (View v : views) setGone(v);
    }
    //endregion

    //region View State Related Methods (Enabled, Disabled)

    /**
     * Call this function to enable the views
     *
     * @param views the multiple views to be enabled
     */
    public static void setEnabled(View... views) {
        for (View v : views) v.setEnabled(true);
    }

    /**
     * Call this function to enable the views, use this when the views are in a list
     * preferable to use this when using ButterKnife.
     *
     * @param views the multiple views to be enabled
     */
    public static void setEnabled(List<View> views) {
        for (View v : views) setEnabled(v);
    }

    /**
     * Call this function to disable the views
     *
     * @param views the multiple views to be disabled
     */
    public static void setDisabled(View... views) {
        for (View v : views) v.setEnabled(false);
    }

    /**
     * Call this function to disable the views, use this when the views are in a list
     * preferable to use this when using ButterKnife.
     *
     * @param views the multiple views to be disabled
     */
    public static void setDisabled(List<View> views) {
        for (View v : views) setDisabled(v);
    }
    //endregion

    /**
     * Call this function to reset the errors in the {@link TextInputLayout}
     *
     * @param textInputLayouts the textInputLayout
     */
    public static void resetTextInputError(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts)
            textInputLayout.setError(null);
    }

    /**
     * Call this function to reset the errors in the {@link TextInputLayout}, use this when the
     * textInputLayouts are in a list. Preferable to use this when using ButterKnife.
     *
     * @param textInputLayouts the multiple views to be disabled
     */
    public static void resetTextInputError(List<TextInputLayout> textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts)
            resetTextInputError(textInputLayout);
    }

    /**
     * Use these methods to vibrate the device.<br/>
     * <b>Note: </b> add the permission
     *
     * @param context      the context.
     * @param milliseconds the time the vibration must continue.
     */
    public static void vibrate(@NonNull Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    /**
     * This creates a simple alert dialogue with an 'OK' positive button(if clickListener is passed
     * and is cancellable. The theme of the alert dialogue is Theme_Material_Light_Dialog_Alert.
     *
     * @param context         the context.
     * @param title           the title of the alert dialogue.
     * @param message         the message that must be displayed.
     * @param onClickListener the click listener for the okButton.
     * @return the AlertDialog that can be displayed using .show() or further customised.
     */
    public static AlertDialog createSimpleAlertDialog(@NonNull Context context,
                                                      @NonNull String title,
                                                      @NonNull String message,
                                                      @Nullable AlertDialog.OnClickListener onClickListener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        if (onClickListener != null)
            builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(true);
        return builder.create();
    }

    //region View Animation Methods

    /**
     * To animate view slide out from left to right
     *
     * @param view the view that must be animated
     */
    public static void slideOutToRight(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        view.startAnimation(animate);
    }

    /**
     * To animate view slide out from right to left
     *
     * @param view the view that must be animated
     */
    public static void slideOutToLeft(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        view.startAnimation(animate);
    }

    /**
     * To animate view slide out from top to bottom
     *
     * @param view the view that must be animated
     */
    public static void slideOutToBottom(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        view.startAnimation(animate);
    }

    /**
     * To animate view slide out from bottom to top
     *
     * @param view the view that must be animated
     */
    public static void slideOutToTop(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });
        view.startAnimation(animate);
    }
    //endregion
}
