package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

public class AuthAlreadySignedInError extends Throwable {
    public static final String ERROR_MESSAGE = "User Already Signed In";

    @NonNull
    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
