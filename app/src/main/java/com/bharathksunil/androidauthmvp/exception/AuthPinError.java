package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

public class AuthPinError extends Throwable {
    public static final String ERROR_INVALID_MESSAGE = "Invalid Pin, Check if Pin has a minimum length of 4";
    public static final String ERROR_INCORRECT_MESSAGE = "Incorrect Pin, Kindly Retry";
    public static final String ERROR_EMPTY_MESSAGE = "Pin Field Cannot be Empty";

    private String mErrorMessage;

    public AuthPinError(@NonNull FormErrorType formErrorType) {
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
