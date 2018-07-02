package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.androidauthmvp.presenter.SignInPresenter.Repository.PasswordResetCallback;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

import static com.bharathksunil.androidauthmvp.presenter.SignInPresenter.Repository;
import static com.bharathksunil.androidauthmvp.presenter.SignInPresenter.Repository.SignInCallback;
import static com.bharathksunil.androidauthmvp.presenter.SignInPresenter.View;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This runs some tests on the SignIn presenter. All the tests must pass.
 *
 * @author Bharath on 26-01-2018.
 */
public class SignInPresenterImplTest {

    private final String CORRECT_EMAIL = "bharathk.sunil.k@gmail.com";
    private final String CORRECT_PASSWORD = "12Bh@rath12";
    private final String INCORRECT_EMAIL = "joey@gmail.com";
    private final String INCORRECT_PASSWORD = "Joey@1234";
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private String[] invalidEmails = new String[]{
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@example.com",
            "Joe Smith <email@example.com>",
            "email@example@example.com",
            ".email@example.com",
            "email.@example.com",
            "email..email@example.com",
            "あいうえお@example.com",
            "email@example.com (Joe Smith)",
            "email@example",
            /*"email@-example.com",
            "email@example.web",*/
            "email@111.222.333.44444",
            "email@example..com",
            "Abc..123@example.com"
    };
    private String[] invalidPasswords =
            new String[]{
                    "@",            //no Normal, Capital Characters & no Digit & Length < 8
                    "1",            //no Normal, Special, Capital Characters & Length < 8
                    "B",            //no Special Characters & no Digits & Length < 8
                    "bh",           //no Special, Capital Characters & no Digit & Length < 8
                    "bh@",          //no Capital Characters & no Digit & Length < 8
                    "12bh@",        //no Capital Characters & Length < 8
                    "12Bh",         //no Special Characters & Length < 8
                    "12Bh@",        //Length < 8
                    //"12bharath12",  //no Special Character & Capital Character
                    //"12bh@rath12",  //no Capital Character
                    //"12Bharath12",  //no Special Characters
                    "Bharath",      //no Digits & Special Characters & Length < 8
                    "Bh@rath"       //no Digits & Length < 8
            };
    @Mock
    private View view;

    private String INVALID_EMAIL() {
        return invalidEmails[new Random().nextInt(invalidEmails.length)];
    }

    private String WEAK_PASSWORD() {
        return invalidPasswords[new Random().nextInt(invalidPasswords.length)];
    }

    //region Presenter->View Interaction Tests

    /**
     * This test checks if the view will be notified if the login is successful
     */
    @Test
    public void successfullySignedInTest() {
        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onUserSignedIn();
    }

    /**
     * This test checks if the presenter will detect empty email field
     */
    @Test
    public void onEmptyEmailEnteredTest() {
        when(view.getEmailField()).thenReturn("");
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.EMPTY));
    }

    /**
     * This test checks if the presenter will detect invalid email fields
     */
    @Test
    public void onInvalidEmailIdEnteredTest() {

        for (String email : invalidEmails) {
            System.out.println("Incorrect Email Passed: " + email);

            when(view.getEmailField()).thenReturn(email);
            when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);
            Repository repository = new Repository() {
                @Override
                public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                       @NonNull SignInCallback signInCallbacks) {
                    signInLogic(email, password, signInCallbacks);
                }

                @Override
                public void resetPasswordLinkedToEmail(@NonNull String email,
                                                       @NonNull PasswordResetCallback passwordResetCallback) {

                }
            };
            SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
            signInPresenter.setView(view);
            signInPresenter.startSignIn();

        }
        verify(view, times(invalidEmails.length)).onEmailError(ArgumentMatchers.eq(FormErrorType.INVALID));

    }

    /**
     * This test checks if the presenter will be able to detect empty password fields
     */
    @Test
    public void onEmptyPasswordEnteredTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn("");

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onPasswordError(ArgumentMatchers.eq(FormErrorType.EMPTY));
    }

    /**
     * This test checks if the presenter will be able to detect invalid and less strong password
     * fields; the fields must have:
     * C1: 8 characters
     * C2: minimum one digit
     * C3: one Special Character
     */
    @Test
    public void onInvalidOrWeakPasswordEnteredTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        for (String password : invalidPasswords) {
            System.out.println("Weak Password Password: " + password);
            when(view.getPasswordField()).thenReturn(password);

            Repository repository = new Repository() {
                @Override
                public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                       @NonNull SignInCallback signInCallbacks) {
                    signInLogic(email, password, signInCallbacks);
                }

                @Override
                public void resetPasswordLinkedToEmail(@NonNull String email,
                                                       @NonNull PasswordResetCallback passwordResetCallback) {

                }
            };
            SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
            signInPresenter.setView(view);
            signInPresenter.startSignIn();
        }
        verify(view, times(invalidPasswords.length)).onPasswordError(ArgumentMatchers.eq(FormErrorType.INVALID));

    }

    //region Reset Password Feature Tests
    @Test
    public void onResetPasswordPressedWithEmailEnteredTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallback) {

            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {
                resetPasswordLogic(email, passwordResetCallback);

            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.forgottenPassword();

        verify(view).onPasswordResetMailSent();
    }

    @Test
    public void onResetPasswordPressedWithEmptyEmailEntered() {

        when(view.getEmailField()).thenReturn("");
        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallback) {

            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {
                passwordResetCallback.onPasswordResetFailed("Some Message");

            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.forgottenPassword();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.EMPTY));
    }

    @Test
    public void onResetPasswordPressedWithUnRegisteredEmailEntered() {

        when(view.getEmailField()).thenReturn(INCORRECT_EMAIL);
        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallback) {

            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {
                resetPasswordLogic(email, passwordResetCallback);

            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.forgottenPassword();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.INCORRECT));
    }
    //endregion
    //endregion

    //region Repository->Presenter->View Interaction Tests

    /**
     * This test will check if the presenter is notified if the email id was not in the repository
     */
    @Test
    public void onEmailIncorrectTest() {

        when(view.getEmailField()).thenReturn(INCORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onEmailError(ArgumentMatchers.eq(FormErrorType.INCORRECT));
    }

    /**
     * This test will check if the presenter is notified if the password had a mismatch
     */
    @Test
    public void onPasswordIncorrectTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(INCORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onPasswordError(ArgumentMatchers.eq(FormErrorType.INCORRECT));

    }

    /**
     * This test will check if the presenter will be notified if there was an unexpected error in
     * the repository
     */
    @Test
    public void onUnexpectedErrorTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInCallbacks.onRepositoryException("Some Message");
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onProcessError("Some Message");

    }

    /**
     * This test will check if the presenter will be notified if there User tries to signIn again
     */
    @Test
    public void detectUserAlreadySignedInTest() {

        when(view.getEmailField()).thenReturn(CORRECT_EMAIL);
        when(view.getPasswordField()).thenReturn(CORRECT_PASSWORD);

        Repository repository = new Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                   @NonNull SignInCallback signInCallbacks) {
                signInCallbacks.isAlreadySignedIn();
            }

            @Override
            public void resetPasswordLinkedToEmail(@NonNull String email,
                                                   @NonNull PasswordResetCallback passwordResetCallback) {

            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.startSignIn();

        verify(view).onUserAlreadySignedIn();

    }
    //endregion

    /**
     * This method emulates the logic of the repository Signing In the User
     *
     * @param email          the email id
     * @param password       the password
     * @param signInCallback the signIn callbacks
     */
    private void signInLogic(@NonNull String email, @NonNull String password,
                             @NonNull SignInCallback signInCallback) {
        if (email.equals(CORRECT_EMAIL)) {
            if (password.equals(CORRECT_PASSWORD)) {
                signInCallback.onSignInSuccessful();
            } else
                signInCallback.onPasswordIncorrect();
        } else
            signInCallback.onEmailIncorrect();
    }

    private void resetPasswordLogic(@NonNull String email,
                                    @NonNull PasswordResetCallback passwordResetCallback) {
        if (email.equals(CORRECT_EMAIL)) {
            passwordResetCallback.onPasswordResetMailSent();
        } else passwordResetCallback.onEmailIncorrectError();
    }
}