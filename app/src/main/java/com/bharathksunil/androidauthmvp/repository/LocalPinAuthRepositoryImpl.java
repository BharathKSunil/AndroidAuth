package com.bharathksunil.androidauthmvp.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.model.UserPin;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenter;

public class LocalPinAuthRepositoryImpl implements PinAuthPresenter.Repository {
    private UserPinDatabase mDBInstance;

    public LocalPinAuthRepositoryImpl(Context applicationContext) {
        this.mDBInstance = Room.databaseBuilder(
                applicationContext,
                UserPinDatabase.class,
                "local_store"
        ).allowMainThreadQueries().build();
    }

    @Override
    public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
        UserPin userPin = mDBInstance.userDao().getUsersPin();
        if (userPin != null) {
            if (userPin.getPin().equals(pin)) {
                callback.validAuthPin();
                mDBInstance.close();
            } else
                callback.invalidAuthPin();
        } else
            callback.onRepositoryError("Pin Not Set: Could Not fetch Pin");
    }

}
