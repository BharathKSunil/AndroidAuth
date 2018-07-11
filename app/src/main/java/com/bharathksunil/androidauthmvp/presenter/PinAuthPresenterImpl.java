package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.androidauthmvp.model.UserPin;
import com.bharathksunil.util.TextUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static io.reactivex.schedulers.Schedulers.io;

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
            viewInstance.onAuthPinFieldError("Pin Cannot be Empty");
        } else if (pin.length() != 4) {
            viewInstance.onAuthPinFieldError("Pin Length must be 4");
        }
        repository.authenticateUserPin(pin)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserPin>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //do nothing
                    }

                    @Override
                    public void onNext(UserPin userPin) {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.pinAuthenticatedSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.onAuthPinFieldError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //do nothing
                    }
                });
    }
}
