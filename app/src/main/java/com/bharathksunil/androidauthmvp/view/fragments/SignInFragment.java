package com.bharathksunil.androidauthmvp.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.R;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenterImpl;
import com.bharathksunil.androidauthmvp.repository.FirebaseSignInRepositoryImpl;
import com.bharathksunil.util.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass to perform SignIn and implements the {@link SignInPresenter.View}
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment implements SignInPresenter.View {

    //region Fragment Methods
    private static final String ARG_APP_ICON_RES = "appIconResource";
    private static final String ARG_APP_NAME_RES = "appNameResource";
    private static final int INPUT_EMAIL = 0, INPUT_PASSWORD = 1;
    //region View Declarations
    @BindView(R.id.iv_app_icon)
    ImageView mAppIconImage;
    @BindView(R.id.tv_app_name)
    TextView mAppName;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindViews({R.id.input_email, R.id.input_password})
    List<TextInputLayout> mTextInputList;
    @BindViews({R.id.btn_submit, R.id.tv_terms_and_privacy_policy, R.id.tv_reset_password, R.id.tv_sign_up})
    List<View> mClickableViewList;
    //endregion
    @BindString(R.string.err_incorrect_email)
    String err_incorrectEmail;
    @BindString(R.string.err_empty_field)
    String err_emptyField;
    @BindString(R.string.err_invalid_field)
    String err_invalidField;
    @BindString(R.string.err_password_security)
    String err_passwordNotStrong;
    @BindString(R.string.err_incorrect_password)
    String err_incorrectPassword;
    @BindString(R.string.snack_password_reset_mail_sent)
    String snack_PasswordResetMailSent;
    @StringRes
    private int mAppNameResource;
    @DrawableRes
    private int mAppIconDrawableResource;
    @SuppressWarnings("NullableProblems")
    @NonNull
    private OnFragmentInteractionListener mListener;
    private Unbinder mUnbinder;
    private SignInPresenter mPresenter;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param appIconDrawable The icon Resource of the app to be displayed
     * @param appNameResource the name of the app to be displayed.
     * @return A new instance of fragment SignInFragment.
     */
    public static SignInFragment newInstance(@DrawableRes int appIconDrawable,
                                             @StringRes int appNameResource) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_APP_ICON_RES, appIconDrawable);
        args.putInt(ARG_APP_NAME_RES, appNameResource);
        fragment.setArguments(args);
        return fragment;
    }

    public void usePresenter(SignInPresenter presenter) {
        mPresenter = presenter;
    }

    //endregion

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            mAppIconDrawableResource = getArguments().getInt(ARG_APP_ICON_RES, R.mipmap.ic_launcher);
            mAppNameResource = getArguments().getInt(ARG_APP_NAME_RES, R.string.app_name);
        } else {
            mAppIconDrawableResource = R.mipmap.ic_launcher;
            mAppNameResource = R.string.app_name;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mAppIconImage.setImageResource(mAppIconDrawableResource);
        mAppName.setText(mAppNameResource);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null) {
            //Newly created Fragment
            mPresenter = new SignInPresenterImpl(new FirebaseSignInRepositoryImpl());
            mPresenter.setView(this);
        } else {
            //existing fragment that came into view
            mPresenter.setView(this);
            onProcessEnded();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.setView(null);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    //region Click Listeners
    @OnClick(R.id.tv_reset_password)
    public void onForgotPasswordPressed() {
        ViewUtils.createSimpleAlertDialog(
                requireContext(),
                "Are you sure you want to reset your password?",
                "A Password reset link will be sent to your email ID",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.forgottenPassword();
                    }
                }
        ).show();
    }

    @OnClick(R.id.tv_sign_up)
    public void onSignUpPressed() {
        ViewUtils.resetTextInputError(mTextInputList);
        mListener.loadSignUpScreen();
    }

    @OnClick(R.id.btn_submit)
    public void onSignInButtonPressed() {
        mPresenter.startSignIn();
    }

    @OnClick(R.id.tv_terms_and_privacy_policy)
    public void onTermsAndPrivacyPolicyPressed() {
        //TODO Start a fragment to show the app's Privacy Policy and Terms.
    }
    //endregion

    //region Presenter Methods
    @Override
    public String getEmailField() {
        //noinspection ConstantConditions
        return mTextInputList.get(INPUT_EMAIL).getEditText().getText().toString();
    }

    @Override
    public String getPasswordField() {
        //noinspection ConstantConditions
        return mTextInputList.get(INPUT_PASSWORD).getEditText().getText().toString();
    }

    @Override
    public void onEmailError(@NonNull FormErrorType errorType) {
        switch (errorType) {
            case EMPTY:
                mTextInputList.get(INPUT_EMAIL).setError(err_emptyField);
                break;
            case INVALID:
                mTextInputList.get(INPUT_EMAIL).setError(err_invalidField);
                break;
            case INCORRECT:
                mTextInputList.get(INPUT_EMAIL).setError(err_incorrectEmail);
                break;
        }
    }

    @Override
    public void onPasswordError(@NonNull FormErrorType errorType) {
        switch (errorType) {
            case EMPTY:
                mTextInputList.get(INPUT_PASSWORD).setError(err_emptyField);
                break;
            case INVALID:
                mTextInputList.get(INPUT_PASSWORD).setError(err_passwordNotStrong);
                break;
            case INCORRECT:
                mTextInputList.get(INPUT_PASSWORD).setError(err_incorrectPassword);
                break;
        }
    }

    @Override
    public void onUserSignedIn() {
        mListener.userSignedInSuccessfully();
    }

    @Override
    public void onUserAlreadySignedIn() {
        mListener.userAlreadySignedIn();
    }

    @Override
    public void onPasswordResetMailSent() {
        ViewUtils.snackBar(requireActivity(), snack_PasswordResetMailSent);
    }

    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.setDisabled(
                mTextInputList.get(INPUT_PASSWORD),
                mTextInputList.get(INPUT_EMAIL)
        );
        ViewUtils.setDisabled(mClickableViewList);
    }

    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(
                mTextInputList.get(INPUT_PASSWORD),
                mTextInputList.get(INPUT_EMAIL)
        );
        ViewUtils.setEnabled(mClickableViewList);
    }

    @Override
    public void onProcessError(String errorMessage) {
        ViewUtils.errorBar(requireActivity(), errorMessage);
        mListener.userSignInCancelledOrFailed();
    }

    //endregion

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void userSignedInSuccessfully();

        void userSignInCancelledOrFailed();

        void userAlreadySignedIn();

        void loadSignUpScreen();
    }
}
