package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.util.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static io.reactivex.schedulers.Schedulers.io;

public class PinAuthPresenterImpl implements PinAuthPresenter {
    @Nullable
    private View mViewInstance;
    @NonNull
    private Repository mRepository;
    private Disposable mDisposable;

    public PinAuthPresenterImpl(@NonNull Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public void setView(@Nullable View view) {
        this.mViewInstance = view;
        if (view == null && mDisposable != null)
            mDisposable.dispose();
    }

    @Override
    public void pinEntered(@NonNull String pin) {
        if (mViewInstance == null)
            return;

        mViewInstance.onProcessStarted();
        if (TextUtils.isEmpty(pin)) {
            mViewInstance.onAuthPinFieldError("Pin Cannot be Empty");
        } else if (pin.length() != 4) {
            mViewInstance.onAuthPinFieldError("Pin Length must be 4");
        }

        mDisposable = mRepository.authenticateUserPin(pin)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userPin -> {
                            if (mViewInstance == null)
                                return;
                            mViewInstance.onProcessEnded();
                            mViewInstance.pinAuthenticatedSuccessfully();
                        },
                        throwable -> {
                            if (mViewInstance == null)
                                return;
                            mViewInstance.onProcessEnded();
                            mViewInstance.onAuthPinFieldError(throwable.getMessage());
                        }
                );
    }
}
