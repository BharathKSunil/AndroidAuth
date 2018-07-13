package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.BaseView;
import com.bharathksunil.androidauthmvp.model.UserPin;

import io.reactivex.Single;

/**
 * This is a presenter which deals with the pin authentication.
 * The {@link View} interface must be implemented by the View to use this presenter and call the
 * {@link #setView(View)} function to pass in the view instance before calling any function in the presenter.
 *
 * @author BharathKSunil
 */
public interface PinAuthPresenter {

    /**
     * This is the method to be called to connect to this presenter as well as to disconnect
     * to connect, pass in the instance of the view and to disconnect pass null.
     *
     * @param view the instance to the view
     */
    void setView(@Nullable View view);

    /**
     * This method is called to start the authentication process
     * this can be called after the user enters the pin.
     *
     * @param pin the pin entered by the user
     */
    void pinEntered(@NonNull final String pin);

    /**
     * The View must implement this interface. this interface is used to communicate between the
     * presenter and the view
     */
    interface View extends BaseView {
        /**
         * This method will be called if there is any error on the Pin field
         *
         * @param errorMessage the user friendly error message.
         */
        void onAuthPinFieldError(@NonNull final String errorMessage);

        /**
         * This method is called once the pin is authenticated and the process stops here.
         * You may replace this view with other here.
         */
        void pinAuthenticatedSuccessfully();
    }

    /**
     * The Repository must implement this interface. The repository must be capable to store the
     * pin securely and this abstracts the backend to the presenter and offers backend independence.
     * You may implement the backend in any fashion you like: REST, Firebase, SQLite etc..
     *
     * @author BharathKSunil
     */
    interface Repository {

        /**
         * Call this function to authenticate the pin entered.
         *
         * @param pin the pin to be authenticated
         */
        Single<UserPin> authenticateUserPin(@NonNull final String pin);
    }
}
