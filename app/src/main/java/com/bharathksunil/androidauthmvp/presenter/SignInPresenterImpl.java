package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.exception.AuthAlreadySignedInError;
import com.bharathksunil.androidauthmvp.exception.AuthEmailError;
import com.bharathksunil.androidauthmvp.exception.AuthPasswordError;
import com.bharathksunil.util.TextUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static io.reactivex.schedulers.Schedulers.io;

/**
 * The SignInPresenter implementation to perform signIn
 * A presenter interacts with both the UI and the repository
 * this is independent of the way the UI or the Repositories are implemented
 *
 * @author Bharath on 26-01-2018.
 */

public class SignInPresenterImpl implements SignInPresenter {

    @Nullable
    private SignInPresenter.View view;
    @NonNull
    private SignInPresenter.Repository repository;


    public SignInPresenterImpl(@NonNull Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@Nullable View view) {
        this.view = view;
    }

    @Override
    public void startSignIn() {
        if (view == null)
            return;
        view.onProcessStarted();
        //get These fields from the view
        String email = view.getEmailField();
        String password = view.getPasswordField();
        if (validateField(email, password))
            repository.signInWithEmailAndPassword(email, password)
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            //nothing to do here
                        }

                        @Override
                        public void onNext(String o) {
                            if (view != null) {
                                view.onProcessEnded();
                                view.onUserSignedIn();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof AuthEmailError && view != null) {
                                view.onProcessEnded();
                                view.onEmailError(e.getMessage());
                            } else if (e instanceof AuthPasswordError && view != null) {
                                view.onProcessEnded();
                                view.onPasswordError(e.getMessage());
                            } else if (e instanceof AuthAlreadySignedInError && view != null) {
                                view.onProcessEnded();
                                view.onUserAlreadySignedIn();
                            } else if (view != null) {
                                view.onProcessEnded();
                                view.onProcessError(e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                            //nothing to do
                        }
                    });
    }

    private boolean validateField(String email, String password) {
        //check syntax and fields
        if (view == null)
            return false;
        if (TextUtils.isEmpty(email)) {
            view.onEmailError("Email Address Cannot be Empty");
            view.onProcessEnded();
            return false;
        } else if (!TextUtils.isEmailValid(email)) {
            view.onEmailError("This is not a Valid Email Address");
            view.onProcessEnded();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            view.onPasswordError("Please enter a password");
            view.onProcessEnded();
            return false;
        } else if (!TextUtils.isPasswordStrong(password)) {
            view.onPasswordError("Kindly enter a strong password");
            view.onProcessEnded();
            return false;
        } else
            return true;
    }

    @Override
    public void forgottenPassword() {
        if (view != null) {
            view.onProcessStarted();
            //get These fields from the view
            String email = view.getEmailField();
            if (TextUtils.isEmpty(email)) {
                view.onEmailError("Email Address Cannot be Empty");
                view.onProcessEnded();
            } else if (!TextUtils.isEmailValid(email)) {
                view.onEmailError("This is not a Valid Email Address");
                view.onProcessEnded();
            } else
                repository.resetPasswordLinkedToEmail(email)
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //nothing to do here
                            }

                            @Override
                            public void onNext(String s) {
                                if (view != null) {
                                    view.onProcessEnded();
                                    view.onPasswordResetMailSent();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof AuthEmailError && view != null) {
                                    view.onProcessEnded();
                                    view.onEmailError(e.getMessage());
                                } else if (view != null) {
                                    view.onProcessEnded();
                                    view.onProcessError(e.getMessage());
                                }
                            }

                            @Override
                            public void onComplete() {
                                //nothing to do here
                            }
                        });
        }
    }
}
