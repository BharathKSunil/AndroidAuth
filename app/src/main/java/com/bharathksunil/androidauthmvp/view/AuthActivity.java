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
import com.bharathksunil.util.Debug;
import com.bharathksunil.util.FragmentTransactionUtils;
import com.bharathksunil.util.SnackBarUtils;

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
        FragmentTransactionUtils.loadFragment(
                this,
                signInFragment,
                R.id.frame_primary,
                SignInFragment.class.getSimpleName(),
                false
        );
    }

    @Override
    public void userSignedInSuccessfully() {
        Debug.i("Signed In");
    }

    @Override
    public void userSignInCancelledOrFailed(@NonNull final String errorMessage) {
        SnackBarUtils.showErrorBar(this, errorMessage);
        Debug.i("Sign in Failed or Cancelled");
    }

    @Override
    public void userAlreadySignedIn() {
        Debug.i("Signed in already");
    }

    @Override
    public void loadSignUpScreen() {
        Debug.i("Load SignUp");
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

        FragmentTransactionUtils.loadFragment(
                this,
                pinAuthFragment,
                R.id.frame_primary,
                PinAuthFragment.class.getSimpleName(),
                false
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
