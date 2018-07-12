package com.bharathksunil.androidauthmvp.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.exception.AuthPinError;
import com.bharathksunil.androidauthmvp.model.UserPin;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenter;

import io.reactivex.Single;

public class LocalPinAuthRepositoryImpl implements PinAuthPresenter.Repository {
    private UserPinDatabase mDBInstance;

    public LocalPinAuthRepositoryImpl(Context applicationContext) {
        this.mDBInstance = Room.databaseBuilder(
                applicationContext,
                UserPinDatabase.class,
                "local_store"
        ).build();
    }

    @Override
    public Single<UserPin> authenticateUserPin(@NonNull final String pin) {
        return Single
                .create(
                        emitter -> {
                            if (emitter.isDisposed())
                                return;
                            UserPin userPin = mDBInstance.userDao().getUsersPin();
                            if (userPin != null) {
                                if (userPin.getPin().equals(pin)) {
                                    emitter.onSuccess(userPin);
                                    mDBInstance.close();
                                } else
                                    emitter.onError(new AuthPinError(FormErrorType.INCORRECT));
                            } else
                                emitter.onError(new Throwable("Pin Not Set"));
                        }
                );
    }

}
