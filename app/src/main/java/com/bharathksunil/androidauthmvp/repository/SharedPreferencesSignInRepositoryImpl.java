package com.bharathksunil.androidauthmvp.repository;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.exception.AuthAlreadySignedInError;
import com.bharathksunil.androidauthmvp.exception.AuthEmailError;
import com.bharathksunil.androidauthmvp.exception.AuthPasswordError;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.bharathksunil.utils.TextUtils;

import io.reactivex.Single;

/**
 * This implements the SignInRepository and performs the sign with SharedPreferences as the backend
 *
 * @author BharathKSunil on 27-01-2018.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
     * @param email    the email id of the user
     * @param password the password of the user
     */
    @Override
    public Single<String> signInWithEmailAndPassword(@NonNull String email, @NonNull String password) {
        //check if the user is already signed in
        if (manager.isUserLoggedIn())
            return Single.error(new AuthAlreadySignedInError());
            //check if the user entered email exists in the shared preferences
        else if (manager.isUserRegistered(email)) {
            //then check if the email entered is correct
            if (manager.validatePassword(email, password)) {
                manager.updateLoginTime(email);
                //show that the signIn was successful
                return Single.just("Welcome, You have been successfully Signed In");
            } else
                //show that the Password Entered was Incorrect
                return Single.error(new AuthPasswordError(FormErrorType.INCORRECT));
        } else
            //show that the email entered is incorrect
            return Single.error(new AuthEmailError(FormErrorType.INCORRECT));

    }

    @Override
    public Single<String> resetPasswordLinkedToEmail(@NonNull String email) {
        if (TextUtils.isEmailValid(email))
            if (manager.isUserRegistered(email)) {
                //TODO: Connect to an email sending api and send the password to their email
                return Single.error(new Throwable("UnImplemented Feature, Contact Admin"));
            } else
                return Single.error(new AuthEmailError(FormErrorType.INCORRECT));
        else
            return Single.error(new AuthEmailError(FormErrorType.INVALID));
    }

    /**
     * This class stores the user login info and all the registered emails with their respective
     * passwords in a shared preferences file.
     * Once a user is registered, the email and corresponding password is stored as email:password
     * where email is the KEY and password is the VALUE.
     * This also stores the SignIn Time of the current user.
     * This uses a key {@link SessionManager#KEY_IS_SIGNED_IN} to store whether a user is currently
     * signed in.
     */
    class SessionManager {
        private static final String KEY_IS_SIGNED_IN = "IsSignedIn";
        private static final String KEY_SIGN_IN_TIME = "Time";
        private SharedPreferences preferences;

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

        String getPassword(@NonNull String email) {
            return preferences.getString(email, "");
        }

        void updateLoginTime(String email) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(email + KEY_SIGN_IN_TIME, "SomeTime");
            editor.apply();
        }

        void signUpUserByEmail(@NonNull String email, @NonNull String password) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(email, password);
            editor.apply();
        }

    }
}
