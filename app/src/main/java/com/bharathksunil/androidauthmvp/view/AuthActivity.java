package com.bharathksunil.androidauthmvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.bharathksunil.androidauthmvp.R;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenterImpl;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenterImpl;
import com.bharathksunil.androidauthmvp.repository.FirebaseSignInRepositoryImpl;
import com.bharathksunil.androidauthmvp.repository.LocalPinAuthRepositoryImpl;
import com.bharathksunil.androidauthmvp.view.fragments.PinAuthFragment;
import com.bharathksunil.androidauthmvp.view.fragments.SignInFragment;
import com.bharathksunil.utils.FragmentTransactionUtils;
import com.bharathksunil.utils.SnackBarUtils;

import timber.log.Timber;

public class AuthActivity extends AppCompatActivity implements SignInFragment.OnFragmentInteractionListener,
        PinAuthFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //loadSignInFragment();
        loadPinAuthFragment();
    }

    //region SignInFragment Listeners
    private void loadSignInFragment() {
        SignInFragment signInFragment = SignInFragment.newInstance(
                R.mipmap.ic_launcher,
                R.string.app_name
        );
        signInFragment.usePresenter(new SignInPresenterImpl(new FirebaseSignInRepositoryImpl()));
        FragmentTransactionUtils.replaceFragment(
                getSupportFragmentManager(),
                signInFragment,
                R.id.frame_primary,
                SignInFragment.class.getSimpleName(),
                false,
                FragmentTransactionUtils.TransitionAnimation.SLIDING_IN_LEFT
        );
    }

    @Override
    public void userSignedInSuccessfully() {
        Timber.i("Signed In");
    }

    @Override
    public void userSignInCancelledOrFailed(@NonNull final String errorMessage) {
        SnackBarUtils.showErrorBar(this, errorMessage);
        Timber.i("Sign in Failed or Cancelled");
    }

    @Override
    public void userAlreadySignedIn() {
        Timber.i("Signed in already");
    }

    @Override
    public void loadSignUpScreen() {
        Timber.i("Load SignUp");
    }

    @Override
    public void loadTermsAndPrivacyPolicyScreen() {
        SnackBarUtils.showLongSnackBar(this, "Will be implemented Soon");

    }
    //endregion

    //region PinAuthFragment Listeners
    private void loadPinAuthFragment() {
        PinAuthFragment pinAuthFragment = PinAuthFragment.newInstance(
                R.mipmap.ic_launcher,
                R.string.app_name
        );
        pinAuthFragment.usePresenter(
                new PinAuthPresenterImpl(
                        new LocalPinAuthRepositoryImpl(
                                getBaseContext()
                        )
                )
        );

        FragmentTransactionUtils.replaceFragment(
                getSupportFragmentManager(),
                pinAuthFragment,
                R.id.frame_primary,
                PinAuthFragment.class.getSimpleName(),
                false,
                FragmentTransactionUtils.TransitionAnimation.FADING

        );
    }

    @Override
    public void pinAuthenticated() {
        SnackBarUtils.showLongSnackBar(this, "Pin Authenticated Successfully");
        loadSignInFragment();
    }

    @Override
    public void pinAuthenticationFailed(@NonNull String errorMessage) {
        SnackBarUtils.showErrorBar(this, errorMessage);
    }

    //endregion
}
