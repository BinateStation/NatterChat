package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.RegistrationFragment;
import rkr.binatestation.natterchat.listeners.RegistrationListener;
import rkr.binatestation.natterchat.utils.SessionUtils;

public class SplashScreenActivity extends BaseActivity implements RegistrationListener {
    private static final String TAG = "SplashScreenActivity";

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setContentView(R.layout.activity_home);
            loadRegistrationFragment();
        } else {
            navigateToHome();
        }
    }


    private void loadRegistrationFragment() {
        Log.d(TAG, "loadRegistrationFragment() called");
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Collections.singletonList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK && response != null) {
                SessionUtils.setPhoneNumber(this, response.getPhoneNumber());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, RegistrationFragment.newInstance(response.getPhoneNumber()))
                        .commit();

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showAlert("Sign in cancelled");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showAlert("No internet connection");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showAlert("Unknown error");
                    return;
                }
            }

            showAlert("Unknown error");
        }
    }

    @Override
    public void onSuccessRegistration(FirebaseUser user) {
        if (user != null) {
            navigateToHome();
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegistrationFailed() {
        showAlert(getString(R.string.registration_failed_msg));
    }
}
