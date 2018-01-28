package com.bharathksunil.androidauthmvp.presenter;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

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

    private String INVALID_EMAIL() {
        String[] invalidEmails = new String[]{"plainaddress",
                "#@%^%#$@#$@#.com",
                "@example.com",
                "Joe Smith <email@example.com>",
                "email.example.com",
                "email@example@example.com",
                ".email@example.com",
                "email.@example.com",
                "email..email@example.com",
                "あいうえお@example.com",
                "email@example.com (Joe Smith)",
                "email@example",
                "email@-example.com",
                "email@example.web",
                "email@111.222.333.44444",
                "email@example..com",
                "Abc..123@example.com"
        };
        return invalidEmails[new Random().nextInt(invalidEmails.length)];
    }

    private String WEAK_PASSWORD() {
        String[] invalidPasswords =
                new String[]{
                        "@",            //no Normal, Capital Characters & no Digit & Length < 8
                        "1",            //no Normal, Special, Capital Characters & Length < 8
                        "B",            //no Special Characters & no Digits & Length < 8
                        "bh",           //no Special, Capital Characters & no Digit & Length < 8
                        "bh@",          //no Capital Characters & no Digit & Length < 8
                        "12bh@",        //no Capital Characters & Length < 8
                        "12Bh",         //no Special Characters & Length < 8
                        "12Bh@",        //Length < 8
                        "12bharath12",  //no Special Character & Capital Character
                        "12bh@rath12",  //no Capital Character
                        "12Bharath12",  //no Special Characters
                        "Bharath",      //no Digits & Special Characters & Length < 8
                        "Bh@rath"       //no Digits & Length < 8
                };
        return invalidPasswords[new Random().nextInt(invalidPasswords.length)];
    }


    //============================Presenter-View Tests======================================/

    /**
     * This test checks if the view will be notified if the login is successful
     */
    @Test
    public void successfullySignedInTest() {

        SignInPresenter.View view = new SignInPresenter.View() {

            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
            }

            @Override
            public String getEmailField() {
                return CORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return CORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onUserSignedIn() {
                Assert.assertEquals(true, true);
            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };

        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

    }

    /**
     * This test checks if the presenter will detect empty email field
     */
    @Test
    public void onEmptyEmailEnteredTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return "";
            }

            @Override
            public String getPasswordField() {
                return CORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.EMPTY, errorType);
            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();
    }

    /**
     * This test checks if the presenter will detect invalid email fields
     */
    @Test
    public void onInvalidEmailIdEnteredTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return INVALID_EMAIL();
            }

            @Override
            public String getPasswordField() {
                return CORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INVALID, errorType);
            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();
    }

    /**
     * This test checks if the presenter will be able to detect empty password fields
     */
    @Test
    public void onEmptyPasswordEnteredTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return CORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return "";
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.EMPTY, errorType);
            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();
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

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return CORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return WEAK_PASSWORD();
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INVALID, errorType);
            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

    }

    //==========================Repository-Presenter Tests============================/

    /**
     * This test will check if the presenter is notified if the email id was not in the repository
     */
    @Test
    public void onEmailIncorrectTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return INCORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return CORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INCORRECT, errorType);
            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();
    }

    /**
     * This test will check if the presenter is notified if the password had a mismatch
     */
    @Test
    public void onPasswordIncorrectTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {

            }

            @Override
            public String getEmailField() {
                return CORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return INCORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INCORRECT, errorType);
            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                signInLogic(email, password, signInCallbacks);
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

    }

    /**
     * This test will check if the presenter will be norified if there was an unexpected error in
     * the repository
     */
    @Test
    public void onUnexpectedErrorTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.assertEquals(1, 1);
            }

            @Override
            public String getEmailField() {
                return CORRECT_EMAIL;
            }

            @Override
            public String getPasswordField() {
                return CORRECT_PASSWORD;
            }

            @Override
            public void onEmailError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onPasswordError(@NonNull FormErrorType errorType) {

            }

            @Override
            public void onUserSignedIn() {

            }

            @Override
            public void onUserAlreadySignedIn() {

            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(@NonNull String email, @NonNull String password, @NonNull SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and something went wrong in the repository
                signInCallbacks.onRepositoryException();
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

    }

    /**
     * This method emulates the logic of the repository
     *
     * @param email           the email id
     * @param password        the password
     * @param signInCallbacks the signIn callbacks
     */
    private void signInLogic(String email, String password, SignInPresenter.Repository.SignInCallbacks signInCallbacks) {
        if (email.equals(CORRECT_EMAIL)) {
            if (password.equals(CORRECT_PASSWORD)) {
                signInCallbacks.onSignInSuccessful();
            } else
                signInCallbacks.onPasswordIncorrect();
        } else
            signInCallbacks.onEmailIncorrect();
    }
}