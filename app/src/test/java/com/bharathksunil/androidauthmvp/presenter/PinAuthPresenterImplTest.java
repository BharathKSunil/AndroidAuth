package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("FieldCanBeLocal")
public class PinAuthPresenterImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private String CORRECT_PIN = "1234";
    private final String INCORRECT_PIN = "2222";
    private final String INVALID_PIN = "123";
    @Mock
    private PinAuthPresenter.View view;

    @Test
    public void correctPinEnteredTest() {
        PinAuthPresenter.Repository repository = new PinAuthPresenter.Repository() {
            @Override
            public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
                if (pin.equals(CORRECT_PIN)) {
                    callback.validAuthPin();
                } else callback.invalidAuthPin();
            }
        };
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(CORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).pinAuthenticatedSuccessfully();
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).onAuthPinFieldError(FormErrorType.INCORRECT);

    }

    @Test
    public void incorrectPinEnteredTest() {
        PinAuthPresenter.Repository repository = new PinAuthPresenter.Repository() {
            @Override
            public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
                if (pin.equals(CORRECT_PIN)) {
                    callback.validAuthPin();
                } else callback.invalidAuthPin();
            }
        };
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(INCORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(FormErrorType.INCORRECT));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void invalidPinEnteredTest() {
        PinAuthPresenter.Repository repository = new PinAuthPresenter.Repository() {
            @Override
            public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
                if (pin.equals(CORRECT_PIN)) {
                    callback.validAuthPin();
                } else callback.invalidAuthPin();
            }
        };
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(INVALID_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(FormErrorType.INVALID));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void emptyPinEnteredTest() {
        PinAuthPresenter.Repository repository = new PinAuthPresenter.Repository() {
            @Override
            public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
                if (pin.equals(CORRECT_PIN)) {
                    callback.validAuthPin();
                } else callback.invalidAuthPin();
            }
        };
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered("");

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(FormErrorType.EMPTY));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void onRepositoryError() {
        PinAuthPresenter.Repository repository = new PinAuthPresenter.Repository() {
            @Override
            public void authenticateUserPin(@NonNull String pin, @NonNull PinAuthCallback callback) {
                callback.onRepositoryError("");
            }
        };
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(CORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onProcessError("");
        //check that these are never called
        verify(view, times(0)).onAuthPinFieldError(FormErrorType.INCORRECT);
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

}