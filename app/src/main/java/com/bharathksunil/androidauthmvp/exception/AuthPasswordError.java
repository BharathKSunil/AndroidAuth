package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

public class AuthPasswordError extends Throwable {
    public static final String ERROR_INVALID_MESSAGE = "Invalid Password, Check if password matches minimum strength";
    public static final String ERROR_INCORRECT_MESSAGE = "Incorrect Password, Kindly Retry";
    public static final String ERROR_EMPTY_MESSAGE = "Password Field Cannot be Empty";
    private String mErrorMessage;

    public AuthPasswordError(@NonNull FormErrorType formErrorType) {
        switch (formErrorType) {
            case INVALID:
                mErrorMessage = ERROR_INVALID_MESSAGE;
                break;
            case INCORRECT:
                mErrorMessage = ERROR_INCORRECT_MESSAGE;
                break;
            case EMPTY:
                mErrorMessage = ERROR_EMPTY_MESSAGE;
                break;
        }
    }

    @NonNull
    @Override
    public String getMessage() {
        return mErrorMessage;
    }
}
