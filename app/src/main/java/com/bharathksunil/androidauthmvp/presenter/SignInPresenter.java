package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.BaseView;

import io.reactivex.Single;

/**
 * This presenter interface is to be used for signing-in to the app
 * the view must implement the {@link View}
 *
 * @author BharathKSunil on 26-01-2018.
 */
public interface SignInPresenter {

    /**
     * This method gets the viewInstance for the presenter to interact
     *
     * @param view the view implementing the {@link SignInPresenter.View} interface or
     *             null to disconnect to the presenter.
     */
    void setView(@Nullable View view);

    /**
     * This method starts the signIn Process.
     */
    void startSignIn();

    /**
     * This method resets the password associated with the emailId
     */
    void forgottenPassword();

    /**
     * Implement this interface on the view (activity or the fragment) for interaction between the
     * presenter and the view. This abstracts the presenter to the view
     */
    interface View extends BaseView {
        /**
         * This method is called by the presenter when it needs to email address
         *
         * @return the emailId from the text field
         */
        String getEmailField();

        /**
         * This method is called when the presenter needs the password
         *
         * @return the password as, entered by the user
         */
        String getPasswordField();

        /**
         * This method is called when there is an error on the EmailId Field passed
         */
        void onEmailError(@NonNull String errorMessage);

        /**
         * This method is called when there is an error on the Password Field passed
         */
        void onPasswordError(@NonNull String errorMessage);

        /**
         * This method is called when the user was successfully signed in
         */
        void onUserSignedIn();

        /**
         * This method is called when the user is already signed in and is trying to sign in again
         */
        void onUserAlreadySignedIn();

        /**
         * This method is called when the password reset mail is sent
         */
        void onPasswordResetMailSent();
    }

    /**
     * Implement this repository to perform various signIn related operations on the repository(backend)
     * this abstracts the backend to the presenter and offers backend independence.
     * You may implement the backend in any fashion you like: REST, Firebase, SQLite etc..
     *
     * @author BharathKSunil on 26-01-2018.
     */
    interface Repository {
        /**
         * This method signs in the user with the email and password
         *
         * @param email    the email id of the user
         * @param password the password of the user
         */
        Single<String> signInWithEmailAndPassword(@NonNull final String email, @NonNull final String password);

        /**
         * This method resets the password linked to the EmailAddress
         *
         * @param email the email of user
         */
        Single<String> resetPasswordLinkedToEmail(@NonNull final String email);
    }
}
