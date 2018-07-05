package com.bharathksunil.androidauthmvp.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.util.TextUtils;

public class PinAuthPresenterImpl implements PinAuthPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repository;

    public PinAuthPresenterImpl(@NonNull Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(@Nullable View view) {
        this.viewInstance = view;
    }

    @Override
    public void pinEntered(@NonNull String pin) {
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();
        if (TextUtils.isEmpty(pin)) {
            viewInstance.onAuthPinFieldError(FormErrorType.EMPTY);
        } else if (pin.length() != 4) {
            viewInstance.onAuthPinFieldError(FormErrorType.INVALID);
        }
        repository.authenticateUserPin(pin, new Repository.PinAuthCallback() {
            @Override
            public void onRepositoryError(final String message) {
                //TODO: Remove this delay from here
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.onProcessError(message);
                    }
                }, 2000);

            }

            @Override
            public void invalidAuthPin() {
                //TODO: Remove this delay from here
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.onAuthPinFieldError(FormErrorType.INCORRECT);
                    }
                }, 2000);

            }

            @Override
            public void validAuthPin() {
                //TODO: Remove this delay from here
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.pinAuthenticatedSuccessfully();
                    }
                }, 2000);

            }
        });
    }
}
