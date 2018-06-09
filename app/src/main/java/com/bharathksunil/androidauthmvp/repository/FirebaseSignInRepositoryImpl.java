package com.bharathksunil.androidauthmvp.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;

/**
 * This implements the {@link SignInPresenter.Repository} and performs the sign with firebase as the backend
 *
 * @author Bharath on 26-01-2018.
 */

public class FirebaseSignInRepositoryImpl implements SignInPresenter.Repository {

    @Override
    public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                           @NonNull final SignInCallback signInCallback) {
        //TODO: We shall write the firebase authentication and stuff here
    }

    @Override
    public void resetPasswordLinkedToEmail(@NonNull String email,
                                           @NonNull PasswordResetCallback passwordResetCallback) {

    }
}
