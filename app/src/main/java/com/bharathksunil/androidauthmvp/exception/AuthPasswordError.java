package com.bharathksunil.androidauthmvp.exception;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

public class AuthPasswordError extends Throwable {
    private String mErrorMessage;

    public AuthPasswordError(@NonNull FormErrorType formErrorType) {
        switch (formErrorType) {
            case INVALID:
                mErrorMessage = "Invalid Password, Check if password matches minimum strength";
                break;
            case INCORRECT:
                mErrorMessage = "Incorrect Password, Kindly Retry";
                break;
            case EMPTY:
                mErrorMessage = "Password Field Cannot be Empty";
                break;
        }
    }

    @NonNull
    @Override
    public String getMessage() {
        return mErrorMessage;
    }
}
