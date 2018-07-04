package com.bharathksunil.androidauthmvp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "UserPin")
public class UserPin {

    public UserPin(@NonNull String uid, String pin) {
        this.uid = uid;
        this.pin = pin;
    }

    @PrimaryKey
    @NonNull
    private String uid;

    @ColumnInfo(name = "pin")
    private String pin;

    //region Getters
    public String getUid() {
        return uid;
    }

    public String getPin() {
        return pin;
    }
    //endregion

    //region Setters
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    //endregion
}
