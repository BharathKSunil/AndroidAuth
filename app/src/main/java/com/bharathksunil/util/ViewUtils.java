package com.bharathksunil.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.bharathksunil.androidauthmvp.R;

import java.util.List;

/**
 * @author Bharath Kumar S on 08-01-2018.
 */

public class ViewUtils {

    /**
     * THIS IS USED TO FOCUS ON ANY VIEW INSIDE A SCROLL VIEW
     *
     * @param view THIS IS THE VIEW INSIDE THE SCROLLVIEW TO WHICH FOCUS IS REQUIRED
     */
    public static void focusOnView(@NonNull final View view, final @NonNull ScrollView scrollView) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    int vLeft = view.getLeft();
                    int vRight = view.getRight();
                    int sWidth = scrollView.getWidth();
                    scrollView.smoothScrollTo(((vLeft + vRight - sWidth) / 2), view.getTop());
                } catch (Exception e) {
                    Debug.e(" Utils.focusOnView : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * THIS IS USED TO MAKE ANY WINDOW A POPUP TYPE WITH 75% BACKGROUND TRANSPARENCY
     * <b>NOTE:</b> THE ACTIVITY MUST BE HAVING A THEME OF OnlineTheme.PopupDisplay
     *
     * @param window THE WINDOW OF THE ACTIVITY, PASSED BY getWindow() METHOD
     */
    public static void makePopupDisplay(@NonNull Window window) {
        try {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.75f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
            window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            Debug.e("Utils.makePopupDisplay(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This function makes a AppSpecific, Themed Snackbar
     *
     * @param message the message to be shown
     */
    public static void snackBar(@NonNull Activity activity, @NonNull String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.rootView), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.accent));
        snackbar.show();
    }

    public static void snackBar(@NonNull Activity activity, @StringRes int message) {
        errorBar(activity, activity.getString(message));
    }

    /**
     * This function makes a AppSpecific, Themed error Snackbar
     *
     * @param message the message to be shown
     */
    public static void errorBar(@NonNull Activity activity, @NonNull String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.rootView), message, 4000);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_dark));
        snackbar.show();
    }

    public static void errorBar(@NonNull Activity activity, @StringRes int message) {
        errorBar(activity, activity.getString(message));
    }

    public static void setVisible(View... views) {
        for (View v : views) v.setVisibility(View.VISIBLE);
    }

    public static void setVisible(List<View> views) {
        for (View v : views) setVisible(v);
    }

    public static void setInvisible(View... views) {
        for (View v : views) v.setVisibility(View.INVISIBLE);
    }

    public static void setInvisible(List<View> views) {
        for (View v : views) setInvisible(v);
    }

    public static void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
            //slideOutToLeft(v);
        }
    }

    public static void setGone(List<View> views) {
        for (View v : views) setGone(v);
    }

    public static void setEnabled(View... views) {
        for (View v : views) v.setEnabled(true);
    }

    public static void setEnabled(List<View> views) {
        for (View v : views) setEnabled(v);
    }

    public static void setDisabled(View... views) {
        for (View v : views) v.setEnabled(false);
    }

    public static void setDisabled(List<View> views) {
        for (View v : views) setDisabled(v);
    }

    public static void resetTextInputError(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setErrorEnabled(true);
        }
    }

    public static void resetTextInputError(List<TextInputLayout> textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts)
            resetTextInputError(textInputLayout);
    }

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

    public static void vibrate(@NonNull Activity activity, long milliseconds) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }

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

    // To animate view slide out from left to right
    public static void slideOutToRight(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from right to left
    public static void slideOutToLeft(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from top to bottom
    public static void slideOutToBottom(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from bottom to top
    public static void slideOutToTop(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animate);
    }
}
