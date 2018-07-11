package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

public class AuthAlreadySignedInError extends Throwable {

    @NonNull
    @Override
    public String getMessage() {
        return "User Already Signed In";
    }
}
