package rkr.binatestation.natterchat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.dialogs.AlertDialogFragment;
import rkr.binatestation.natterchat.fragments.dialogs.ProgressDialogFragment;


/**
 * Created by RKR on 22/10/2017.
 * BaseFragment
 */

public class BaseFragment extends Fragment {

    public static final String TAG_PROGRESS_DIALOG = "progress_dialog";
    private ProgressDialogFragment mProgressDialogFragment;
    private boolean showDialog;

    protected void setTitle(String title) {
        try {
            getActivity().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout != null) {
            setColorSchemeResources(swipeRefreshLayout);
        }
    }

    protected void requestFocus(EditText view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


    public void showProgress(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    public void hideProgress(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    protected void setColorSchemeResources(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.progress_red,
                R.color.progress_blue,
                R.color.progress_yellow,
                R.color.progress_green
        );
    }

    /**
     * gets the instance of ProgressDialogFragment
     *
     * @return instance of ProgressDialogFragment
     */
    public ProgressDialogFragment getProgress() {
        if (mProgressDialogFragment == null) {
            mProgressDialogFragment = ProgressDialogFragment.newInstance();
        }
        return mProgressDialogFragment;
    }

    /**
     * show progress wheel
     */
    public void showProgressWheel() {
        if (isShowDialog()) {
            if (getProgress().isAdded()) {
                return;
            }
            if (getProgress().isShowing()) {
                return;
            }
            getProgress().show(getChildFragmentManager(), TAG_PROGRESS_DIALOG);
        }
    }

    /**
     * hide progress wheel
     */
    public void hideProgressWheel() {
        if (isShowDialog()) {
            if (!getProgress().isAdded()) {
                return;
            }
            if (!getProgress().isShowing()) {
                return;
            }
            getProgress().dismiss();
        }
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowDialog(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setShowDialog(false);
    }

    /**
     * show alert message
     *
     * @param message String value
     * @return instance of AlertDialogFragment
     */
    public AlertDialogFragment showAlert(String message) {
        final AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(
                getString(android.R.string.dialog_alert_title), message);
        alertDialogFragment.setPositiveButton(getString(android.R.string.yes));
        if (isShowDialog()) {
            alertDialogFragment.show(getChildFragmentManager(), alertDialogFragment.getTag());
        }
        return alertDialogFragment;
    }

    /**
     * show alert message
     *
     * @param message String value
     * @param title   String value
     * @return instance of AlertDialogFragment
     */
    public AlertDialogFragment showAlert(String message, String title) {
        final AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes));
        alertDialogFragment.setPositiveButton(getString(android.R.string.yes));
        if (isShowDialog()) {
            alertDialogFragment.show(getChildFragmentManager(), alertDialogFragment.getTag());
        }
        return alertDialogFragment;
    }
}
