package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends BaseActivity {
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
