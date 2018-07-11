package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

public class AuthEmailError extends Throwable {
    private String mErrorMessage;

    public AuthEmailError(@NonNull FormErrorType formErrorType) {
        switch (formErrorType) {
            case INVALID:
                mErrorMessage = "Invalid Email Address";
                break;
            case INCORRECT:
                mErrorMessage = "Incorrect Email Address";
                break;
            case EMPTY:
                mErrorMessage = "Email Field Cannot be Empty";
                break;
        }
    }

    @NonNull
    @Override
    public String getMessage() {
        return mErrorMessage;
    }
}
