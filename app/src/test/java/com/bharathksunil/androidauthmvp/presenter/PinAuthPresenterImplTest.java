package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.exception.AuthPinError;
import com.bharathksunil.androidauthmvp.model.UserPin;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.ThreadFactory;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

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

    @BeforeClass
    public static void setupClass() {
        RxJavaPlugins.setIoSchedulerHandler(handler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(handler -> Schedulers.trampoline());
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }

    @Test
    public void correctPinEnteredTest() {
        PinAuthPresenter.Repository repository = pin -> Single.create(
                emitter -> {
                    if (pin.equals(CORRECT_PIN))
                        emitter.onSuccess(new UserPin("something", CORRECT_PIN));
                    else
                        emitter.onError(new AuthPinError(FormErrorType.INCORRECT));
                }
        );
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(CORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).pinAuthenticatedSuccessfully();
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).onAuthPinFieldError("");

    }

    @Test
    public void incorrectPinEnteredTest() {
        PinAuthPresenter.Repository repository = pin -> Single.create(
                emitter -> {
                    if (pin.equals(CORRECT_PIN))
                        emitter.onSuccess(new UserPin("something", CORRECT_PIN));
                    else
                        emitter.onError(new AuthPinError(FormErrorType.INCORRECT));
                }
        );
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(INCORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(AuthPinError.ERROR_INCORRECT_MESSAGE));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void invalidPinEnteredTest() {
        PinAuthPresenter.Repository repository = pin -> Single.create(
                emitter -> {
                    if (pin.equals(CORRECT_PIN))
                        emitter.onSuccess(new UserPin("something", CORRECT_PIN));
                    else
                        emitter.onError(new AuthPinError(FormErrorType.INVALID));
                }
        );
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(INVALID_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(AuthPinError.ERROR_INVALID_MESSAGE));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void emptyPinEnteredTest() {
        PinAuthPresenter.Repository repository = pin -> Single.create(
                emitter -> {
                    if (pin.equals(CORRECT_PIN))
                        emitter.onSuccess(new UserPin("something", CORRECT_PIN));
                    else
                        emitter.onError(new AuthPinError(FormErrorType.EMPTY));
                }
        );
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered("");

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onAuthPinFieldError(ArgumentMatchers.eq(AuthPinError.ERROR_EMPTY_MESSAGE));
        //check that these are never called
        verify(view, times(0)).onProcessError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

    @Test
    public void onRepositoryError() {
        PinAuthPresenter.Repository repository = pin -> Single.create(
                emitter -> {
                    emitter.onError(new Throwable("Repository Error"));
                }
        );
        PinAuthPresenter presenter = new PinAuthPresenterImpl(repository);
        presenter.setView(view);
        presenter.pinEntered(CORRECT_PIN);

        //check if these were called
        verify(view).onProcessStarted();
        verify(view).onProcessEnded();
        verify(view).onProcessError("Repository Error");
        //check that these are never called
        verify(view, times(0)).onAuthPinFieldError("");
        verify(view, times(0)).pinAuthenticatedSuccessfully();
    }

}