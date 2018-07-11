package com.bharathksunil.androidauthmvp.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.model.UserPin;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
    public Observable<UserPin> authenticateUserPin(@NonNull final String pin) {
        return Observable.create(
                new ObservableOnSubscribe<UserPin>() {
                    @Override
                    public void subscribe(ObservableEmitter<UserPin> emitter) {
                        UserPin userPin = mDBInstance.userDao().getUsersPin();
                        if (userPin != null) {
                            if (userPin.getPin().equals(pin)) {
                                emitter.onNext(userPin);
                                mDBInstance.close();
                            } else
                                emitter.onError(new Throwable("Incorrect Pin, Retry"));
                        } else
                            emitter.onError(new Throwable("Pin Not Set"));
                    }
                }
        );
    }

}
