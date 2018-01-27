package com.bharathksunil.androidauthmvp.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;

/**
 * This implements the SignInRepository and performs the sign with firebase as the backend
 * @author Bharath on 26-01-2018.
 */

public class FirebaseSignInRepositoryImpl implements SignInPresenter.Repository {
    /**
     * This method sign in the user with the email and password
     *
     * @param email     the email id of the user
     * @param password  the password of the user
     * @param signInCallbacks callbacks to the presenter
     */
    @Override
    public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, final SignInCallbacks signInCallbacks) {
        //TODO: We shall write the firebase authentication and stuff here
    }
}
