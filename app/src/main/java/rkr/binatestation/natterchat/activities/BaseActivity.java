package rkr.binatestation.natterchat.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.dialogs.AlertDialogFragment;
import rkr.binatestation.natterchat.fragments.dialogs.ProgressDialogFragment;


public class BaseActivity extends AppCompatActivity {

    public static final String TAG_PROGRESS_DIALOG = "progress_dialog";
    private boolean showDialog;
    private ActionBar actionBar = null;
    private ProgressDialogFragment mProgressDialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setShowDialog(true);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout != null) {
            setColorSchemeResources(swipeRefreshLayout);
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        setShowDialog(false);
    }

    public void setBackActionEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    public void setTitle(String title) {
        if (getActiveActionBar() != null) {
            getActiveActionBar().setTitle(title);
        }
    }

    protected ActionBar getActiveActionBar() {
        if (actionBar == null) {
            actionBar = getSupportActionBar();
        }
        return actionBar;
    }

    /**
     * show alert message
     *
     * @param message String value
     * @return instance of AlertDialogFragment
     */
    public AlertDialogFragment showAlert(String message) {
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(
                getString(android.R.string.dialog_alert_title), message);
        alertDialogFragment.setPositiveButton(getString(android.R.string.yes));
        if (isShowDialog()) {
            alertDialogFragment.show(getSupportFragmentManager(), alertDialogFragment.getTag());
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
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes));
        alertDialogFragment.setPositiveButton(getString(android.R.string.yes));
        if (isShowDialog()) {
            alertDialogFragment.show(getSupportFragmentManager(), alertDialogFragment.getTag());
        }
        return alertDialogFragment;
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
            getProgress().show(getSupportFragmentManager(), TAG_PROGRESS_DIALOG);
        }
    }

    /**
     * hide progress wheel
     */
    public void hideProgressWheel() {
        getProgress().dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
