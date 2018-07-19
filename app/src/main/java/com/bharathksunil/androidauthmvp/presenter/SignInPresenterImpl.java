package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.exception.AuthAlreadySignedInError;
import com.bharathksunil.androidauthmvp.exception.AuthEmailError;
import com.bharathksunil.androidauthmvp.exception.AuthPasswordError;
import com.bharathksunil.utils.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static io.reactivex.schedulers.Schedulers.io;

/**
 * The {@link SignInPresenter} implementation to perform signIn.
 * A presenter interacts with both the UI and the mRepository
 * this is independent of the way the UI or the Repositories are implemented
 *
 * @author BharathKSunil on 26-01-2018.
 */
public class SignInPresenterImpl implements SignInPresenter {

    @Nullable
    private SignInPresenter.View mViewInstance;
    @NonNull
    private SignInPresenter.Repository mRepository;
    private Disposable mDisposable;

    public SignInPresenterImpl(@NonNull Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public void setView(@Nullable View view) {
        this.mViewInstance = view;
        if (view == null && mDisposable != null)
            mDisposable.dispose();
    }

    @Override
    public void startSignIn() {
        if (mViewInstance == null)
            return;
        mViewInstance.onProcessStarted();
        //get These fields from the mViewInstance
        String email = mViewInstance.getEmailField();
        String password = mViewInstance.getPasswordField();
        if (!validateField(email, password))
            return;

        mDisposable = mRepository.signInWithEmailAndPassword(email, password)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onSuccessMessage -> {
                            if (mViewInstance != null) {
                                mViewInstance.onProcessEnded();
                                mViewInstance.onUserSignedIn();
                            }
                        },
                        throwable -> {
                            if (mViewInstance == null)
                                return;
                            if (throwable instanceof AuthEmailError) {
                                mViewInstance.onProcessEnded();
                                mViewInstance.onEmailError(throwable.getMessage());
                            } else if (throwable instanceof AuthPasswordError) {
                                mViewInstance.onProcessEnded();
                                mViewInstance.onPasswordError(throwable.getMessage());
                            } else if (throwable instanceof AuthAlreadySignedInError) {
                                mViewInstance.onProcessEnded();
                                mViewInstance.onUserAlreadySignedIn();
                            } else {
                                mViewInstance.onProcessEnded();
                                mViewInstance.onProcessError(throwable.getMessage());
                            }
                        }
                );
    }

    private boolean validateField(String email, String password) {
        //check syntax and fields
        if (mViewInstance == null)
            return false;
        if (TextUtils.isEmpty(email)) {
            mViewInstance.onEmailError(AuthEmailError.ERROR_EMPTY_MESSAGE);
            mViewInstance.onProcessEnded();
            return false;
        } else if (!TextUtils.isEmailValid(email)) {
            mViewInstance.onEmailError(AuthEmailError.ERROR_INVALID_MESSAGE);
            mViewInstance.onProcessEnded();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            mViewInstance.onPasswordError(AuthPasswordError.ERROR_EMPTY_MESSAGE);
            mViewInstance.onProcessEnded();
            return false;
        } else if (!TextUtils.isPasswordStrong(password)) {
            mViewInstance.onPasswordError(AuthPasswordError.ERROR_INVALID_MESSAGE);
            mViewInstance.onProcessEnded();
            return false;
        } else
            return true;
    }

    @Override
    public void forgottenPassword() {
        if (mViewInstance != null) {
            mViewInstance.onProcessStarted();
            //get These fields from the mViewInstance
            String email = mViewInstance.getEmailField();

            if (TextUtils.isEmpty(email)) {
                mViewInstance.onEmailError(AuthEmailError.ERROR_EMPTY_MESSAGE);
                mViewInstance.onProcessEnded();
            } else if (!TextUtils.isEmailValid(email)) {
                mViewInstance.onEmailError(AuthEmailError.ERROR_INVALID_MESSAGE);
                mViewInstance.onProcessEnded();
            } else
                mDisposable = mRepository.resetPasswordLinkedToEmail(email)
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                onSuccessMessage -> {
                                    if (mViewInstance != null) {
                                        mViewInstance.onProcessEnded();
                                        mViewInstance.onPasswordResetMailSent();
                                    }
                                },
                                throwable -> {
                                    if (throwable instanceof AuthEmailError && mViewInstance != null) {
                                        mViewInstance.onProcessEnded();
                                        mViewInstance.onEmailError(throwable.getMessage());
                                    } else if (mViewInstance != null) {
                                        mViewInstance.onProcessEnded();
                                        mViewInstance.onProcessError(throwable.getMessage());
                                    }
                                }
                        );
        }
    }
}
