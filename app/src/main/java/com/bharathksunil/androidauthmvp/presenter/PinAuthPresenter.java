package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.BaseView;
import com.bharathksunil.androidauthmvp.model.UserPin;

import io.reactivex.Observable;

/**
 * This presenter deals with the 4 digit pin authentication screen.
 * The {@link View} interface must be implemented by the View to use this presenter and call the
 * {@link #setView(View)} function to pass in the view instance before calling any function in the presenter.
 */
public interface PinAuthPresenter {
    void setView(@Nullable View view);

    void pinEntered(@NonNull String pin);

    interface View extends BaseView {
        void onAuthPinFieldError(@NonNull String errorMessage);

        void pinAuthenticatedSuccessfully();
    }

    interface Repository {
        Observable<UserPin> authenticateUserPin(@NonNull String pin);
    }
}
