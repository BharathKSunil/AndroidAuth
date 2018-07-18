package com.bharathksunil.androidauthmvp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharathksunil.androidauthmvp.R;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenter;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenterImpl;
import com.bharathksunil.androidauthmvp.repository.LocalPinAuthRepositoryImpl;
import com.bharathksunil.util.SnackBarUtils;
import com.bharathksunil.util.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.bharathksunil.util.ViewUtils.vibrate;

/**
 * A simple {@link Fragment} subclass to perform the Pin Authentication and implements the
 * {@link PinAuthPresenter.View}.
 * <h4>Highlights</h4><br/>
 * * 1. App Icon and a heading text can be passed during runtime to customise it.<br/>
 * 2. The Fragment can work with different {@link PinAuthPresenter} implementations<br/>
 * 3. And also different {@link PinAuthPresenter.Repository} implementations<br/>
 * <h4>How to Use:</h4><br/>
 * 1. Activities that use this fragment <b>must implement the {@link OnFragmentInteractionListener}
 * interface</b> to handle interaction events.<br/>
 * 2. Use the static method {@link #newInstance(int IconResource, int StringResource)} to create an
 * instance of this fragment and <b>Immediately</b> call the method {@link #usePresenter(PinAuthPresenter)}
 * before the fragment commits or transaction completes in order to pass the presenter that the
 * Fragment must use, if not passed/called then it uses {@link PinAuthPresenterImpl}
 * as the Presenter Implementation with the {@link LocalPinAuthRepositoryImpl} as the Repository.
 */
public class PinAuthFragment extends Fragment implements PinAuthPresenter.View {

    //region CONSTANTS
    private static final String BUNDLE_APP_ICON_RES = "appIconResource";
    private static final String BUNDLE_APP_NAME_RES = "appNameStringResource";
    private static final int CONST_MAX_PIN_LENGTH = 4;
    //endregion

    //region View Declarations
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView mLoadingIndicatorView;
    @BindViews({R.id.iv_pin_one, R.id.iv_pin_two, R.id.iv_pin_three, R.id.iv_pin_four})
    List<ImageView> ivPinList;
    @BindView(R.id.ll_circles)
    LinearLayout mPinLayout;
    @BindViews({R.id.btn_pin_one, R.id.btn_pin_two, R.id.btn_pin_three, R.id.btn_pin_four,
            R.id.btn_pin_five, R.id.btn_pin_six, R.id.btn_pin_seven, R.id.btn_pin_eight,
            R.id.btn_pin_nine, R.id.btn_pin_zero, R.id.btn_pin_backspace})
    List<View> mButtonsListView;
    @BindView(R.id.iv_app_icon)
    ImageView mAppIcon;
    @BindView(R.id.tv_app_name)
    TextView mAppName;
    //endregion

    @StringRes
    private int mAppNameResource;
    @DrawableRes
    private int mAppIconDrawableResource;

    //to interact with the Activity
    private OnFragmentInteractionListener mFragmentInteractionListener;
    private Unbinder mUnbinder; //to unbind ButterKnife from fragment
    private PinAuthPresenter mPresenter;
    /**
     * This is a stack which stores the user input of numbers on the number pads
     */
    private Deque<String> mPinStack;

    /**
     * Default Constructor. Use newInstance() to create an instance instead.
     */
    public PinAuthFragment() {
        // Required empty public constructor
    }

    //region Fragment Setup Methods

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param appIconDrawable The icon Resource of the app to be displayed
     * @param appNameResource the name of the app to be displayed.
     * @return A new instance of fragment SignInFragment.
     */
    public static PinAuthFragment newInstance(@DrawableRes int appIconDrawable,
                                              @StringRes int appNameResource) {
        PinAuthFragment fragment = new PinAuthFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_APP_ICON_RES, appIconDrawable);
        args.putInt(BUNDLE_APP_NAME_RES, appNameResource);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Call this method soon after the the instance has be created to pass in the desired presenter
     *
     * @param presenter the {@link PinAuthPresenter} Implementation with the Repository
     */
    public void usePresenter(PinAuthPresenter presenter) {
        mPresenter = presenter;
    }
    //endregion

    //region Fragment Callbacks
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //here we check if the Activity has implemented the InteractionInterface
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            //throw an exception if the developer has forgotten to implement.
            throw new SecurityException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPinStack = new ArrayDeque<>();
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
        View view = inflater.inflate(R.layout.fragment_pin_auth, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mAppIcon.setImageResource(mAppIconDrawableResource);
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
        if (mPresenter != null) {
            //Newly created Fragment with no presenter passed
            mPresenter.setView(this);
            onProcessEnded();
        } else {
            //existing fragment that came into view or new fragment with a presenter passed
            mPresenter = new PinAuthPresenterImpl(
                    new LocalPinAuthRepositoryImpl(
                            requireActivity().getApplicationContext()
                    )
            );
            mPresenter.setView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
    }
    //endregion

    //region Presenter Methods
    @Override
    public void onAuthPinFieldError(@NonNull String errorMessage) {
        vibrate(requireActivity(), 300);
        SnackBarUtils.showErrorBar(requireActivity(), errorMessage);
    }

    @Override
    public void pinAuthenticatedSuccessfully() {
        mFragmentInteractionListener.pinAuthenticated();
    }

    @Override
    public void onProcessStarted() {
        mLoadingIndicatorView.smoothToShow();
        ViewUtils.setDisabled(mButtonsListView);
    }

    @Override
    public void onProcessEnded() {
        for (int i = mPinStack.size(); i > 0; i--)
            backspacePressed();
        mLoadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(mButtonsListView);
    }

    @Override
    public void onProcessError(String errorMessage) {
        mFragmentInteractionListener.pinAuthenticationFailed(errorMessage);
    }
    //endregion

    //region View Click Listeners
    @OnClick({R.id.btn_pin_one, R.id.btn_pin_two, R.id.btn_pin_three, R.id.btn_pin_four,
            R.id.btn_pin_five, R.id.btn_pin_six, R.id.btn_pin_seven, R.id.btn_pin_eight,
            R.id.btn_pin_nine, R.id.btn_pin_zero, R.id.btn_pin_backspace})
    public void onViewClicked(View view) {
        vibrate(requireActivity(), 30);
        switch (view.getId()) {
            case R.id.btn_pin_one:
                numberEntered("1");
                break;
            case R.id.btn_pin_two:
                numberEntered("2");
                break;
            case R.id.btn_pin_three:
                numberEntered("3");
                break;
            case R.id.btn_pin_four:
                numberEntered("4");
                break;
            case R.id.btn_pin_five:
                numberEntered("5");
                break;
            case R.id.btn_pin_six:
                numberEntered("6");
                break;
            case R.id.btn_pin_seven:
                numberEntered("7");
                break;
            case R.id.btn_pin_eight:
                numberEntered("8");
                break;
            case R.id.btn_pin_nine:
                numberEntered("9");
                break;
            case R.id.btn_pin_zero:
                numberEntered("0");
                break;
            case R.id.btn_pin_backspace:
                backspacePressed();
                break;
            default:
                break;
        }
    }
    //endregion

    /**
     * This method is to be called when a number is pressed and the logic of inserting it is done here.
     * Once the Count of digits reach {@link #CONST_MAX_PIN_LENGTH}, the app calls the presenter to
     * process the pin.
     *
     * @param number the number that was pressed on the number-pad
     */
    private void numberEntered(@NonNull String number) {
        if (mPinStack.size() < CONST_MAX_PIN_LENGTH) {
            mPinStack.push(number); //push the number to the deque
            //animation of the bubble
            ivPinList.get(mPinStack.size() - 1)
                    .setImageResource(R.drawable.pin_background_activated);
        }
        if (mPinStack.size() == CONST_MAX_PIN_LENGTH) {
            final StringBuilder password = new StringBuilder();
            for (String s : mPinStack) {
                password.append(s);
            }
            mPresenter.pinEntered(password.reverse().toString());
        }
    }

    /**
     * This method is to be called when the user presses the backspace button and the logic of
     * removing is done here.
     */
    private void backspacePressed() {
        if (!mPinStack.isEmpty()) {
            mPinStack.pop();//pop the number last entered from the deque
            //animation of the bubble
            ivPinList.get(mPinStack.size())
                    .setImageResource(R.drawable.pin_background_normal);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        /**
         * This method is called when the pin authentication is successfully competed
         * You may proceed to removing the fragment or replacing it
         */
        void pinAuthenticated();

        /**
         * This method will be called when the pin authentication Failed due to some repository error
         *
         * @param errorMessage printable error message which indicate the error
         */
        void pinAuthenticationFailed(@NonNull final String errorMessage);
    }
}
