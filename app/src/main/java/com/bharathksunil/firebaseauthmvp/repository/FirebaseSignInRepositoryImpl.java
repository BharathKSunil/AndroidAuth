package com.bharathksunil.firebaseauthmvp.repository;

import com.bharathksunil.firebaseauthmvp.presenter.SignInPresenter;

/**
 * This implements the
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
    public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
        //TODO: We shall write the firebase authentication and stuff here
    }
}
