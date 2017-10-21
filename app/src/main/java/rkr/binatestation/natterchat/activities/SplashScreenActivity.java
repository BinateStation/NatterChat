package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends BaseActivity {
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            intent = new Intent(this, HomeActivity.class);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            intent = new Intent(this, RegistrationActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
