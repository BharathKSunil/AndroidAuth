package com.bharathksunil.firebaseauthmvp.presenter;

import android.support.annotation.Nullable;

import com.bharathksunil.firebaseauthmvp.BaseView;

/**
 * This interface is to be used for signIn to the app by the view
 * the view must implement the
 * @author Bharath on 26-01-2018.
 */

public interface SignInPresenter {

    /**
     * Implement this interface on the view (activity or the fragment) for interaction between the
     * presenter and the view. This abstracts the presenter to the view
     */
    interface View extends BaseView {
        /**
         * This method is called by the presenter when it needs to email address
         * @return the emailId from the text field
         */
        String getEmailField();
        /**
         * This method is called when the presenter needs the password
         * @return the password as, entered by the user
         */
        String getPasswordField();
        /**
         * This method is called when there is an error on the EmailId Field passed
         * @param errorType the type of error indicating what kind of error was found in the field
         */
        void onEmailError(FormErrorType errorType);
        /**
         * This method is called when there is an error on the Password Field passed
         * @param errorType the type of error indicating what kind of error was found in the field
         */
        void onPasswordError(FormErrorType errorType);

        /**
         * This method is called when the user was successfully signed in
         */
        void onUserSignedIn();
    }

    /**
     * Implement this repository to perform various signIn related operations on the repository(backend)
     * this abstracts the backend to the presenter and offers backend independence.
     * You may implement the backend in any fashion you like: REST, Firebase, SQLite etc..
     * @author Bharath on 26-01-2018.
     */
    interface Repository {
        /**
         * This is the callback interface for the SignIn method to interact with the presenter
         */
        interface SignInCallbacks {
            /**
             * Called when the signIn was Successful
             */
            void onSignInSuccessful();

            /**
             * Called when the Email provided was incorrect
             * i.e., was not registered in the repository
             */
            void onEmailIncorrect();

            /**
             * Called when the password provided was incorrect
             */
            void onPasswordIncorrect();

            /**
             * Called whenever there was an exception in processing the request
             */
            void onRepositoryException();
        }

        /**
         * This method sign in the user with the email and password
         * @param email the email id of the user
         * @param password the password of the user
         */
        void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks);

    }

    /**
     * Call this method in the onResume to set the current view
     * @param view the SignInPresenter.View
     */
    void setView(@Nullable View view);

    /**
     * Call this method to perform SignIn
     */
    void onSignInButtonClicked();

    /**
     * Call this method to perform a sign in with the email and password
     * @param email the email Id of the user
     * @param password the corresponding password credential

    void signInViaEmailAndPassword(String email, String password);*/

    /*
    void sendPhoneVerificationCode(String phoneNo);
    void resendPhoneVerificationCode();
    void signInViaPhoneNumber(String phoneNo, String pin);

    void signUpUser(String name, String dateOfBirth, String sex);*/
}
