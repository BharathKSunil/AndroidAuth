package com.bharathksunil.androidauthmvp.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.bharathksunil.util.TextUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/**
 * This implements the {@link SignInPresenter.Repository} and performs the sign with firebase as the backend
 *
 * @author Bharath on 26-01-2018.
 */

public class FirebaseSignInRepositoryImpl implements SignInPresenter.Repository {

    @Override
    public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                           @NonNull final SignInCallback signInCallback) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            signInCallback.isAlreadySignedIn();
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email,
                password
        ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                signInCallback.onSignInSuccessful();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException)
                    signInCallback.onPasswordIncorrect();
                else if (e instanceof FirebaseAuthInvalidUserException)
                    signInCallback.onEmailIncorrect();
                else
                    signInCallback.onRepositoryException(e.getMessage());
            }
        });
    }

    @Override
    public void resetPasswordLinkedToEmail(@NonNull String email,
                                           @NonNull final PasswordResetCallback passwordResetCallback) {
        if (TextUtils.isEmailValid(email)) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            passwordResetCallback.onPasswordResetMailSent();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthEmailException)
                                passwordResetCallback.onEmailIncorrectError();
                            else passwordResetCallback.onPasswordResetFailed(e.getMessage());
                        }
                    });
        } else
            passwordResetCallback.onEmailIncorrectError();
    }
}
