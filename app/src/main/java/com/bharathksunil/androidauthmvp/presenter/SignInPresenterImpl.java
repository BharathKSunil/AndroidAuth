package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.util.TextUtils;

import static com.bharathksunil.androidauthmvp.presenter.SignInPresenter.Repository.PasswordResetCallback;
import static com.bharathksunil.androidauthmvp.presenter.SignInPresenter.Repository.SignInCallback;

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
    private SignInCallback signInCallback = new SignInCallback() {
        @Override
        public void onSignInSuccessful() {
            if (view != null) {
                view.onProcessEnded();
                view.onUserSignedIn();
            }
        }

        @Override
        public void onEmailIncorrect() {
            if (view != null) {
                view.onProcessEnded();
                view.onEmailError(FormErrorType.INCORRECT);
            }
        }

        @Override
        public void onPasswordIncorrect() {
            if (view != null) {
                view.onProcessEnded();
                view.onPasswordError(FormErrorType.INCORRECT);
            }
        }

        @Override
        public void onRepositoryException(String message) {
            if (view != null) {
                view.onProcessEnded();
                view.onProcessError(message);
            }
        }

        @Override
        public void isAlreadySignedIn() {
            if (view != null) {
                view.onProcessEnded();
                view.onUserAlreadySignedIn();
            }
        }
    };
    private PasswordResetCallback passwordResetCallback = new PasswordResetCallback() {
        @Override
        public void onPasswordResetMailSent() {
            if (view != null) {
                view.onProcessEnded();
                view.onPasswordResetMailSent();
            }
        }

        @Override
        public void onPasswordResetFailed(@NonNull String message) {
            if (view != null) {
                view.onProcessEnded();
                view.onEmailError(FormErrorType.INCORRECT);
                view.onProcessError(message);
            }
        }
    };

    public SignInPresenterImpl(@NonNull Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@Nullable View view) {
        this.view = view;
    }

    @Override
    public void startSignIn() {
        if (view != null) {
            view.onProcessStarted();
            //get These fields from the view
            String email = view.getEmailField();
            String password = view.getPasswordField();
            //check syntax and fields
            if (TextUtils.isEmpty(email)) {
                view.onEmailError(FormErrorType.EMPTY);
                view.onProcessEnded();
            } else if (!TextUtils.isEmailValid(email)) {
                view.onEmailError(FormErrorType.INVALID);
                view.onProcessEnded();
            } else if (TextUtils.isEmpty(password)) {
                view.onPasswordError(FormErrorType.EMPTY);
                view.onProcessEnded();
            } else if (!TextUtils.isPasswordStrong(password)) {
                view.onPasswordError(FormErrorType.INVALID);
                view.onProcessEnded();
            }
            //if all are valid proceed with signIn
            else
                repository.signInWithEmailAndPassword(email, password, signInCallback);
        }
    }

    @Override
    public void forgottenPassword() {
        if (view != null) {
            view.onProcessStarted();
            //get These fields from the view
            String email = view.getEmailField();
            if (TextUtils.isEmpty(email)) {
                view.onEmailError(FormErrorType.EMPTY);
                view.onProcessEnded();
            } else if (!TextUtils.isEmailValid(email)) {
                view.onEmailError(FormErrorType.INVALID);
                view.onProcessEnded();
            } else
                repository.resetPasswordLinkedToEmail(email, passwordResetCallback);
        }
    }
}
