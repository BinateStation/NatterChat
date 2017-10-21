package rkr.binatestation.natterchat.fragments.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rkr.binatestation.natterchat.R;


/**
 * Alert dialog fragment
 */
public class AlertDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_POSITIVE_BUTTON_TEXT = "positive_button_text";
    private static final String KEY_NEGATIVE_BUTTON_TEXT = "negative_button_text";
    private static final String KEY_NEUTRAL_BUTTON_TEXT = "neutral_button_text";
    private static final String KEY_SHOW_AS_DIALOG = "SHOW_AS_DIALOG";
    private static final String TAG = "AlertDialogFragment";

    private String mTitle, mMessage, mPositiveButtonText, mNegativeButtonText, mNeutralButtonText, mIconString;

    @DrawableRes
    private int mIcon;

    private Toolbar mToolbar;
    private TextView mMessageTextView;
    private Button mActionPositiveButton, mActionNegativeButton, mActionNeutralButton;
    private ImageView iconImageView;
    private View toolbarLayoutView;

    private DialogInterface.OnClickListener mOnClickListener;
    private View mRootView;

    public AlertDialogFragment() {
        // Required empty public constructor
    }

//    public static AlertDialogFragment newInstance(ErrorModel errorModel) {
//        Log.d(TAG, "newInstance() called with: errorModel = [" + errorModel + "]");
//        Bundle args = new Bundle();
//        args.putString(KEY_TITLE, errorModel.getErrorTitle());
//        args.putString(KEY_MESSAGE, errorModel.getErrorMessage());
//        AlertDialogFragment fragment = new AlertDialogFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static AlertDialogFragment newInstance(String title, String message) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertDialogFragment newInstance(boolean showAsDialog, String message) {
        Log.d(TAG, "newInstance() called with: showAsDialog = [" + showAsDialog + "], message = [" + message + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, "");
        args.putBoolean(KEY_SHOW_AS_DIALOG, showAsDialog);
        args.putString(KEY_MESSAGE, message);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertDialogFragment newInstance(String title, String message, String positiveButtonText) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "], positiveButtonText = [" + positiveButtonText + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertDialogFragment newInstance(String title, String message, String positiveButtonText, String negativeButtonText) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "], positiveButtonText = [" + positiveButtonText + "], negativeButtonText = [" + negativeButtonText + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
            mMessage = bundle.getString(KEY_MESSAGE);
            if (bundle.containsKey(KEY_POSITIVE_BUTTON_TEXT)) {
                mPositiveButtonText = bundle.getString(KEY_POSITIVE_BUTTON_TEXT);
            }
            if (bundle.containsKey(KEY_NEGATIVE_BUTTON_TEXT)) {
                mNegativeButtonText = bundle.getString(KEY_NEGATIVE_BUTTON_TEXT);
            }
            if (bundle.containsKey(KEY_NEUTRAL_BUTTON_TEXT)) {
                mNeutralButtonText = bundle.getString(KEY_NEUTRAL_BUTTON_TEXT);
            }
            setShowsDialog(bundle.getBoolean(KEY_SHOW_AS_DIALOG, true));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar = view.findViewById(R.id.toolbar);
        iconImageView = view.findViewById(R.id.icon);
        toolbarLayoutView = view.findViewById(R.id.app_bar_layout);
        mMessageTextView = view.findViewById(R.id.message);
        mActionPositiveButton = view.findViewById(R.id.action_positive);
        mActionNegativeButton = view.findViewById(R.id.action_negative);
        mActionNeutralButton = view.findViewById(R.id.action_neutral);

        mActionPositiveButton.setOnClickListener(this);
        mActionNegativeButton.setOnClickListener(this);
        mActionNeutralButton.setOnClickListener(this);

        if (!TextUtils.isEmpty(mMessage)) {
            setData();
        }
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setMessage(String message) {
        this.mMessage = message;
        if (mMessageTextView != null) {
            if (!TextUtils.isEmpty(message)) {
                mMessageTextView.setVisibility(View.VISIBLE);
                mMessageTextView.setText(message);
            } else {
                mMessageTextView.setVisibility(View.GONE);
            }

        }
    }

    public void setIcon(String icon) {
        this.mIconString = icon;
        if (iconImageView != null) {
            if (!TextUtils.isEmpty(icon)) {
                iconImageView.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(icon)
                        .into(iconImageView);
            }
        }


    }

    public void setIcon(@DrawableRes int icon) {
        this.mIcon = icon;
        if (iconImageView != null) {
            if (icon != 0) {
                iconImageView.setVisibility(View.VISIBLE);
                iconImageView.setImageResource(icon);
            }
        }

    }

    public void setTitle(String title) {
        this.mTitle = title;
        if (mToolbar != null) {
            if (!TextUtils.isEmpty(title)) {
                if (toolbarLayoutView != null) {
                    toolbarLayoutView.setVisibility(View.VISIBLE);
                }
                mToolbar.setTitle(title);
            } else {
                if (toolbarLayoutView != null) {
                    toolbarLayoutView.setVisibility(View.GONE);
                }
            }
        }

    }

    public void setPositiveButton(String label) {
        this.mPositiveButtonText = label;
        if (mActionPositiveButton != null) {
            if (!TextUtils.isEmpty(label)) {
                mActionPositiveButton.setVisibility(View.VISIBLE);
                mActionPositiveButton.setText(label);
            } else {
                mActionPositiveButton.setVisibility(View.GONE);
            }
        }

    }

    public void setNegativeButton(String label) {
        this.mNegativeButtonText = label;
        if (mActionNegativeButton != null) {
            if (!TextUtils.isEmpty(label)) {
                mActionNegativeButton.setVisibility(View.VISIBLE);
                mActionNegativeButton.setText(label);
            } else {
                mActionNegativeButton.setVisibility(View.GONE);
            }
        }

    }

    public void setNeutralButton(String label) {
        this.mNeutralButtonText = label;
        if (mActionNeutralButton != null) {
            if (!TextUtils.isEmpty(label)) {
                mActionNeutralButton.setVisibility(View.VISIBLE);
                mActionNeutralButton.setText(label);
            } else {
                mActionNeutralButton.setVisibility(View.GONE);
            }
        }
    }

    private void setData() {
        setTitle(mTitle);
        setMessage(mMessage);
        setPositiveButton(mPositiveButtonText);
        setNegativeButton(mNegativeButtonText);
        setNeutralButton(mNeutralButtonText);
        setIcon(mIconString);
        setIcon(mIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_positive:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                }
                break;
            case R.id.action_negative:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
                }
                break;
            case R.id.action_neutral:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(getDialog(), DialogInterface.BUTTON_NEUTRAL);
                }
                break;

        }
        if (getShowsDialog() && isVisible()) {
            dismiss();
        }
    }
}
