package com.bharathksunil.androidauthmvp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "UserPin")
public class UserPin {

    @PrimaryKey
    @NonNull
    private String uid;
    @ColumnInfo(name = "pin")
    private String pin;

    public UserPin(@NonNull String uid, String pin) {
        this.uid = uid;
        this.pin = pin;
    }

    //region Getters
    public String getUid() {
        return uid;
    }

    //region Setters
    public void setUid(String uid) {
        this.uid = uid;
    }
    //endregion

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    //endregion
}
