package com.bharathksunil.androidauthmvp.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.exception.AuthAlreadySignedInError;
import com.bharathksunil.androidauthmvp.exception.AuthEmailError;
import com.bharathksunil.androidauthmvp.exception.AuthPasswordError;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * This implements the {@link SignInPresenter.Repository} and performs the sign with firebase as the backend
 *
 * @author Bharath on 26-01-2018.
 */

public class FirebaseSignInRepositoryImpl implements SignInPresenter.Repository {

    @Override
    public Observable<String> signInWithEmailAndPassword(@NonNull final String email, @NonNull final String password) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return Observable.error(new AuthAlreadySignedInError());
        }

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        email,
                        password
                ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        emitter.onNext("Welcome, You have been successfully signed in");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException)
                            emitter.onError(new AuthPasswordError(FormErrorType.INCORRECT));
                        else if (e instanceof FirebaseAuthInvalidUserException)
                            emitter.onError(new AuthEmailError(FormErrorType.INCORRECT));
                        else
                            emitter.onError(new Throwable(e.getLocalizedMessage()));
                    }
                });
            }
        });
    }

    @Override
    public Observable<String> resetPasswordLinkedToEmail(@NonNull final String email) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext("Password Reset mail sent");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseAuthEmailException || e instanceof FirebaseAuthInvalidUserException)
                                    emitter.onError(new AuthEmailError(FormErrorType.INCORRECT));
                                else
                                    emitter.onError(e.getCause());
                            }
                        });
            }
        });
    }
}
