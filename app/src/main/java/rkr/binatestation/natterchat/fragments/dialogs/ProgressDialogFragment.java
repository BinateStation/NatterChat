package rkr.binatestation.natterchat.fragments.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import rkr.binatestation.natterchat.R;


/**
 * Dialog fragment to show progress dialog.
 */
public class ProgressDialogFragment extends DialogFragment {


    private Dialog dialog;

    public ProgressDialogFragment() {
        // Required empty public constructor
    }

    public static ProgressDialogFragment newInstance() {

        Bundle args = new Bundle();

        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme_Translucent);
        fragment.setCancelable(false);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProgressBar progressBar = new ProgressBar(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(layoutParams);
        return progressBar;
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
