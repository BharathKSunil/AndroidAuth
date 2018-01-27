package com.bharathksunil.androidauthmvp.presenter;

import org.junit.Assert;
import org.junit.Test;

/**
 * This runs some tests on the SignIn presenter. All the tests must pass.
 *
 * @author Bharath on 26-01-2018.
 */
public class SignInPresenterImplTest {

    //============================Presenter-View Tests======================================/

    /**
     * This test checks if the view will be notified if the login is successful
     */
    @Test
    public void onSuccessfulSignInTest() {

        SignInPresenter.View view = new SignInPresenter.View() {

            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "24Bh@rath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onUserSignedIn() {
                Assert.assertEquals(true, true);
            }
        };

        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and did the sign in and it was successful
                signInCallbacks.onSignInSuccessful();
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
    public void onEmailEmptyTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "";
            }

            @Override
            public String getPasswordField() {
                return "24Bharath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.EMPTY, errorType);
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and will do the sign here
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
    public void onEmailInvalidTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bhaarathk46@m";
            }

            @Override
            public String getPasswordField() {
                return "24Bharath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INVALID, errorType);
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and will do the sign here
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
    public void onPasswordEmptyTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.EMPTY, errorType);
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and will do the sign here
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
    public void onPasswordInvalidTest() {

        SignInPresenter.View view = new SignInPresenter.View() {
            @Override
            public void onProcessStarted() {
            }

            @Override
            public void onProcessEnded() {
            }

            @Override
            public void onUnexpectedError() {
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "24Bharath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INVALID, errorType);
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and will do the sign here
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
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "24Bh@rath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INCORRECT, errorType);
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and tried to sign in but the email is not registered
                signInCallbacks.onEmailIncorrect();
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
                Assert.fail();
            }

            @Override
            public String getEmailField() {
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "24Bh@rath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.assertEquals(FormErrorType.INCORRECT, errorType);
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and the passwords didn't match
                signInCallbacks.onPasswordIncorrect();
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
                return "bharathk.sunil.k@gmail.com";
            }

            @Override
            public String getPasswordField() {
                return "24Bh@rath1996";
            }

            @Override
            public void onEmailError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onPasswordError(FormErrorType errorType) {
                Assert.fail();
            }

            @Override
            public void onUserSignedIn() {
                Assert.fail();
            }
        };
        SignInPresenter.Repository repository = new SignInPresenter.Repository() {
            @Override
            public void signInWithEmailAndPassword(String email, String password, SignInCallbacks signInCallbacks) {
                //lets assume we reached to the repository and something went wrong in the repository
                signInCallbacks.onRepositoryException();
            }
        };
        SignInPresenterImpl signInPresenter = new SignInPresenterImpl(repository);
        signInPresenter.setView(view);
        signInPresenter.onSignInButtonClicked();

    }

}