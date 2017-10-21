package rkr.binatestation.natterchat.activities;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.UserFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setBackActionEnabled();

        setUserView();
    }


    private void setUserView() {
        UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.user_fragment);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (userFragment != null && user != null) {
            userFragment.setUserModel(user);
        }
    }
}
