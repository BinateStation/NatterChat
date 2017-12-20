package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.RegistrationFragment;
import rkr.binatestation.natterchat.listeners.RegistrationListener;

public class SplashScreenActivity extends BaseActivity implements RegistrationListener {
    private static final String TAG = "SplashScreenActivity";

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RegistrationFragment.newInstance())
                .commit();
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
