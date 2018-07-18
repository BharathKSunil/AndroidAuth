package com.bharathksunil.androidauthmvp.view.fragments;

import android.content.Context;
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

import com.bharathksunil.androidauthmvp.R;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenter;
import com.bharathksunil.androidauthmvp.presenter.SignInPresenterImpl;
import com.bharathksunil.androidauthmvp.repository.FirebaseSignInRepositoryImpl;
import com.bharathksunil.util.SnackBarUtils;
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
 * It is highly generalised to make it very easy to plug and use.
 * <h4>Highlights</h4><br/>
 * 1. App Icon and a heading text can be passed during runtime to customise it.<br/>
 * 2. The Fragment can work with different {@link SignInPresenter} implementations<br/>
 * 3. And also different {@link SignInPresenter.Repository} implementations<br/>
 * <h4>How to Use:</h4><br/>
 * 1. Activities that use this fragment <b>must implement the {@link OnFragmentInteractionListener}
 * interface</b> to handle interaction events.<br/>
 * 2. Use the static method {@link #newInstance(int IconResource, int StringResource)} to create an
 * instance of this fragment and <b>Immediately</b> call the method {@link #usePresenter(SignInPresenter)}
 * before the fragment commits or transaction completes in order to pass the presenter that the
 * Fragment must use, if not passed/called then it uses {@link SignInPresenterImpl}
 * as the Presenter Implementation with the {@link FirebaseSignInRepositoryImpl} as the Repository.
 *
 * @author BharathKSunil
 */
public class SignInFragment extends Fragment implements SignInPresenter.View {

    //region CONSTANTS
    private static final String BUNDLE_APP_ICON_RES = "appIconResource";
    private static final String BUNDLE_APP_NAME_RES = "appNameStringResource";
    //positions of TextInputLayout in the list mTextInputLayoutList
    private static final int INPUT_EMAIL = 0; //this corresponds to the position of the email field
    private static final int INPUT_PASSWORD = 1; //this corresponds to the position of the password field
    //endregion

    //region View Declarations
    @BindView(R.id.iv_app_icon)
    ImageView mAppIconImage;
    @BindView(R.id.tv_app_name)
    TextView mAppName;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindViews({R.id.input_email, R.id.input_password})
    List<TextInputLayout> mTextInputList;
    @BindViews({R.id.btn_submit, R.id.tv_terms_and_privacy_policy, R.id.tv_reset_password, R.id.btn_sign_up})
    List<View> mClickableViewList; //clickable views that need to be disabled once process starts
    @BindString(R.string.snack_password_reset_mail_sent)
    String snackPasswordResetMailSent;
    //endregion

    @StringRes
    private int mAppNameResource;
    @DrawableRes
    private int mAppIconDrawableResource;

    private OnFragmentInteractionListener mFragmentInteractionListener; //to interact with the Activity
    private Unbinder mUnbinder; //to unbind ButterKnife from fragment
    private SignInPresenter mPresenter;

    /**
     * Default Constructor. Use newInstance() to create an instance instead.
     */
    public SignInFragment() {
        // Required empty public constructor
    }

    //region Fragment Setup Methods

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @param appIconDrawable The icon Resource that need to be displayed
     * @param appNameResource the Heading/App name that needs to be displayed
     * @return A new instance of fragment SignInFragment.
     */
    public static SignInFragment newInstance(@DrawableRes int appIconDrawable,
                                             @StringRes int appNameResource) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_APP_ICON_RES, appIconDrawable);
        args.putInt(BUNDLE_APP_NAME_RES, appNameResource);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Call this method soon after the the instance has be created to pass in the desired presenter
     *
     * @param presenter the {@link SignInPresenter} Implementation with the Repository
     */
    public void usePresenter(SignInPresenter presenter) {
        mPresenter = presenter;
    }
    //endregion

    //region Fragment Lifecycle Callbacks
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //here we check if the Activity has implemented the InteractionInterface
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            //throw an exception if the developer has forgotten to implement.
            throw new SecurityException(context.toString()
                    + " must implement OnFragmentInteractionListener to use this fragment.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load all the arguments
        if (this.getArguments() != null) {
            mAppIconDrawableResource = getArguments().getInt(BUNDLE_APP_ICON_RES, R.mipmap.ic_launcher);
            mAppNameResource = getArguments().getInt(BUNDLE_APP_NAME_RES, R.string.app_name);
        } else {
            //if no arguments then use default
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
    public void onPause() {
        super.onPause();
        //disconnect from the presenter as we do not want any view updates when the view is not active
        if (mPresenter != null)
            mPresenter.setView(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        //reconnect to the presenter as the view is active
        if (mPresenter == null) {
            //Newly created Fragment with no presenter passed
            mPresenter = new SignInPresenterImpl(new FirebaseSignInRepositoryImpl());
            mPresenter.setView(this);
        } else {
            //existing fragment that came into view or new fragment with a presenter passed
            mPresenter.setView(this);
            onProcessEnded();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
    //endregion

    //region View Click Listeners
    @OnClick(R.id.tv_reset_password)
    public void onForgotPasswordPressed() {
        ViewUtils.createSimpleAlertDialog(
                requireContext(),
                "Are you sure you want to reset your password?",
                "A Password reset link will be sent to your email ID",
                (dialogInterface, i) -> mPresenter.forgottenPassword()
        ).show();
    }

    @OnClick(R.id.btn_sign_up)
    public void onSignUpPressed() {
        mFragmentInteractionListener.loadSignUpScreen();
    }

    @OnClick(R.id.btn_submit)
    public void onSignInButtonPressed() {
        ViewUtils.resetTextInputError(mTextInputList);
        mPresenter.startSignIn();
    }

    @OnClick(R.id.tv_terms_and_privacy_policy)
    public void onTermsAndPrivacyPolicyPressed() {
        mFragmentInteractionListener.loadTermsAndPrivacyPolicyScreen();
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
    public void onEmailError(@NonNull String errorMessage) {
        mTextInputList.get(INPUT_EMAIL).setError(errorMessage);
    }

    @Override
    public void onPasswordError(@NonNull String errorMessage) {
        mTextInputList.get(INPUT_PASSWORD).setError(errorMessage);
    }

    @Override
    public void onUserSignedIn() {
        //tell the activity that the user was signed in
        mFragmentInteractionListener.userSignedInSuccessfully();
    }

    @Override
    public void onUserAlreadySignedIn() {
        //tell the activity that the user is already signed in
        mFragmentInteractionListener.userAlreadySignedIn();
    }

    @Override
    public void onPasswordResetMailSent() {
        SnackBarUtils.showLongSnackBar(requireActivity(), snackPasswordResetMailSent);
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
        //Tell the activity that the signIn process failed
        mFragmentInteractionListener.userSignInCancelledOrFailed(errorMessage);
    }

    //endregion

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        /**
         * This method will be called if the user is successfully signed in and the process is complete
         * You may display a Message here and take appropriate actions after signIn.
         * Also detach the SignInFragment.
         */
        void userSignedInSuccessfully();

        /**
         * This method is called when there was a error with the repository and the process failed
         *
         * @param errorMessage the error message which can be displayed.
         */
        void userSignInCancelledOrFailed(@NonNull final String errorMessage);

        /**
         * This method is called when the User was already signed in and the process ended
         */
        void userAlreadySignedIn();

        /**
         * This method is called when the user wants to signUp and clicked SignUp Button
         */
        void loadSignUpScreen();

        /**
         * This method is called when the user presses the Terms and privacy view button
         * Load a screen to display it here.
         */
        void loadTermsAndPrivacyPolicyScreen();
    }
}
