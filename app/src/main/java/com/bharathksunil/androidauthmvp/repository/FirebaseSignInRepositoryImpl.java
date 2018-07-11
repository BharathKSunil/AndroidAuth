package com.bharathksunil.androidauthmvp.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.exception.AuthAlreadySignedInError;
import com.bharathksunil.androidauthmvp.exception.AuthEmailError;
import com.bharathksunil.androidauthmvp.exception.AuthPasswordError;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import io.reactivex.Single;

/**
 * This implements the {@link SignInPresenter.Repository} and performs the sign with firebase as the backend
 *
 * @author Bharath on 26-01-2018.
 */

public class FirebaseSignInRepositoryImpl implements SignInPresenter.Repository {

    @Override
    public Single<String> signInWithEmailAndPassword(@NonNull final String email, @NonNull final String password) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return Single.error(new AuthAlreadySignedInError());
        }
        return Single.create(
                emitter -> FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            if (emitter.isDisposed())
                                return;
                            emitter.onSuccess("Welcome, You have been successfully signed in");
                        })
                        .addOnFailureListener(e -> {
                            if (emitter.isDisposed())
                                return;
                            if (e instanceof FirebaseAuthInvalidCredentialsException)
                                emitter.onError(new AuthPasswordError(FormErrorType.INCORRECT));
                            else if (e instanceof FirebaseAuthInvalidUserException)
                                emitter.onError(new AuthEmailError(FormErrorType.INCORRECT));
                            else
                                emitter.onError(new Throwable(e.getLocalizedMessage()));
                        })
        );
    }

    @Override
    public Single<String> resetPasswordLinkedToEmail(@NonNull final String email) {
        return Single
                .create(
                        emitter -> FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnSuccessListener(aVoid -> {
                                    if (!emitter.isDisposed())
                                        emitter.onSuccess("Password Reset mail sent");
                                })
                                .addOnFailureListener(e -> {
                                    if (emitter.isDisposed())
                                        return;
                                    if (e instanceof FirebaseAuthEmailException || e instanceof FirebaseAuthInvalidUserException)
                                        emitter.onError(new AuthEmailError(FormErrorType.INCORRECT));
                                    else
                                        emitter.onError(e.getCause());
                                })
                );
    }
}
