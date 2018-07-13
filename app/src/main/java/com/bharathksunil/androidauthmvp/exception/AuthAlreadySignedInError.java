package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

/**
 * This throwable is thrown when the User is signed In and an attempt to signIn is made Again
 */
public class AuthAlreadySignedInError extends Throwable {
    private static final String ERROR_MESSAGE = "User Already Signed In";

    @NonNull
    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
