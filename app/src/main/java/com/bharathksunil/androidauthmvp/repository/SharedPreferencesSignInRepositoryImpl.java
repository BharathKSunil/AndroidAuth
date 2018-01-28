package com.bharathksunil.androidauthmvp.repository;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.bharathksunil.util.TextUtils;

/**
 * This implements the SignInRepository and performs the sign with SharedPreferences as the backend
 *
 * @author Bharath on 27-01-2018.
 */

public class SharedPreferencesSignInRepositoryImpl implements SignInPresenter.Repository {

    /**
     * The name of the shared preferences file where the SignInPreferences will be stored
     */
    public static final String SIGN_IN_PREFERENCES = "SignInPreference";

    /**
     * The shared preferences instance passed to the repository from the view
     */
    private SessionManager manager;

    public SharedPreferencesSignInRepositoryImpl(@NonNull SharedPreferences sharedPreferences) {
        manager = new SessionManager(sharedPreferences);
    }

    /**
     * This method sign in the user with the email and password
     *
     * @param email           the email id of the user
     * @param password        the password of the user
     * @param signInCallbacks callbacks to the presenter
     */
    @Override
    public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                           SignInCallbacks signInCallbacks) {
        if (signInCallbacks == null)
            return;
        //check if the user is already signed in
        if (manager.isUserLoggedIn())
            signInCallbacks.isAlreadySignedIn();
            //check if the user entered email exists in the shared preferences
        else if (manager.isUserRegistered(email)) {
            //then check if the email entered is correct
            if (manager.validatePassword(email, password)) {
                manager.updateLoginTime(email);
                signInCallbacks.onSignInSuccessful();   //show that the signIn was successful
            } else
                signInCallbacks.onPasswordIncorrect();  //show that the Password Entered was Incorrect
        } else
            signInCallbacks.onEmailIncorrect();         //show that the email entered is incorrect

    }

    private class SessionManager {
        private SharedPreferences preferences;

        private static final String KEY_IS_SIGNED_IN = "IsSignedIn";
        private static final String KEY_SIGN_IN_TIME = "Time";

        SessionManager(SharedPreferences preferences) {
            this.preferences = preferences;
        }

        boolean isUserLoggedIn() {
            return preferences.getBoolean(KEY_IS_SIGNED_IN, false);
        }

        boolean isUserRegistered(String email) {
            return preferences.contains(email);
        }

        boolean validatePassword(String email, String password) {
            return TextUtils.areEqual(preferences.getString(email, ""), password);
        }

        void updateLoginTime(String email) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(email + KEY_SIGN_IN_TIME, "SomeTime");
            editor.apply();
        }

    }
}
