package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.BaseView;
import com.bharathksunil.androidauthmvp.FormErrorType;

/**
 * This presenter deals with the 4 digit pin authentication screen.
 * The {@link View} interface must be implemented by the View to use this presenter and call the
 * {@link #setView(View)} function to pass in the view instance before calling any function in the presenter.
 */
public interface PinAuthPresenter {
    void setView(@Nullable View view);

    void pinEntered(@NonNull String pin);

    interface View extends BaseView {
        void onAuthPinFieldError(@NonNull FormErrorType formErrorType);

        void pinAuthenticatedSuccessfully();
    }

    interface Repository {
        void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback);

        interface PinAuthCallback {
            void onRepositoryError(String message);

            void invalidAuthPin();

            void validAuthPin();
        }
    }
}
