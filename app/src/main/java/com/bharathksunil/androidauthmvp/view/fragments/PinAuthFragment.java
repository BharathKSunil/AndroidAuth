package com.bharathksunil.androidauthmvp.view.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

import com.bharathksunil.androidauthmvp.FormErrorType;
import com.bharathksunil.androidauthmvp.R;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenter;
import com.bharathksunil.androidauthmvp.presenter.PinAuthPresenterImpl;
import com.bharathksunil.androidauthmvp.repository.LocalPinAuthRepositoryImpl;
import com.bharathksunil.util.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PinAuthFragment extends Fragment implements PinAuthPresenter.View {

    private static final String ARG_APP_ICON_RES = "appIconResource";
    private static final String ARG_APP_NAME_RES = "appNameResource";
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
    @StringRes
    private int mAppNameResource;
    @DrawableRes
    private int mAppIconDrawableResource;
    @BindView(R.id.iv_app_icon)
    ImageView mAppIcon;
    @BindView(R.id.tv_app_name)
    TextView mAppName;
    //endregion

    private OnFragmentInteractionListener mListener;
    private Unbinder mUnbinder;
    private Stack<String> mPinStack;
    private PinAuthPresenter mPresenter;

    public PinAuthFragment() {
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
    public static PinAuthFragment newInstance(@DrawableRes int appIconDrawable,
                                              @StringRes int appNameResource) {
        PinAuthFragment fragment = new PinAuthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_APP_ICON_RES, appIconDrawable);
        args.putInt(ARG_APP_NAME_RES, appNameResource);
        fragment.setArguments(args);
        return fragment;
    }

    //region Fragment Callbacks
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPinStack = new Stack<>();
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
        View view = inflater.inflate(R.layout.fragment_pin_auth, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mAppIcon.setImageResource(mAppIconDrawableResource);
        mAppName.setText(mAppNameResource);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            //fragment is back in view
            mPresenter.setView(this);
            onProcessEnded();
        } else {
            //fragment is newly created
            mPresenter = new PinAuthPresenterImpl(
                    new LocalPinAuthRepositoryImpl(
                            requireActivity().getApplicationContext()
                    )
            );
            mPresenter.setView(this);
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
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

    //region Presenter Methods
    @Override
    public void onAuthPinFieldError(@NonNull FormErrorType formErrorType) {
        vibrate(300);
        switch (formErrorType) {
            case EMPTY:
                ViewUtils.errorBar(requireActivity(), R.string.err_empty_field);
                break;
            case INCORRECT:
                ViewUtils.errorBar(requireActivity(), R.string.err_incorrect_pin);
                break;
            case INVALID:
                ViewUtils.errorBar(requireActivity(), R.string.err_invalid_field);
                break;
        }
    }

    @Override
    public void pinAuthenticatedSuccessfully() {
        mListener.pinAuthenticated();
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
        ViewUtils.errorBar(requireActivity(), errorMessage);
    }
    //endregion

    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    @OnClick({R.id.btn_pin_one, R.id.btn_pin_two, R.id.btn_pin_three, R.id.btn_pin_four,
            R.id.btn_pin_five, R.id.btn_pin_six, R.id.btn_pin_seven, R.id.btn_pin_eight,
            R.id.btn_pin_nine, R.id.btn_pin_zero, R.id.btn_pin_backspace})
    public void onViewClicked(View view) {
        vibrate(30);
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
        }
    }

    private void numberEntered(@NonNull String number) {
        if (mPinStack.size() < 4) {
            mPinStack.push(number);
            ivPinList.get(mPinStack.size() - 1).setImageResource(R.drawable.pin_background_activated);
        }
        if (mPinStack.size() == 4) {
            final StringBuilder password = new StringBuilder();
            for (String s : mPinStack) {
                password.append(s);
            }
            mPresenter.pinEntered(password.toString());
        }
    }

    private void backspacePressed() {
        if (mPinStack.size() > 0) {
            mPinStack.pop();
            ivPinList.get(mPinStack.size()).setImageResource(R.drawable.pin_background_normal);
        }
    }

    public interface OnFragmentInteractionListener {
        void pinAuthenticated();

        //void pinAuthenticationFailed();
    }
}
