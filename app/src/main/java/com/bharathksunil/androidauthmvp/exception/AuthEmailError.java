package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

/**
 * This Throwable is thrown if there is an error with the Email field
 */
public class AuthEmailError extends Throwable {
    public static final String ERROR_INVALID_MESSAGE = "Invalid Email Address";
    public static final String ERROR_INCORRECT_MESSAGE = "Incorrect Email Address";
    public static final String ERROR_EMPTY_MESSAGE = "Email Field Cannot be Empty";
    private String mErrorMessage;

    public AuthEmailError(@NonNull FormErrorType formErrorType) {
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
