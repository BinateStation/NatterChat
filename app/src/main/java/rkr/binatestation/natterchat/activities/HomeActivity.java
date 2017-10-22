package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.ChatContactListFragment;
import rkr.binatestation.natterchat.fragments.RegistrationFragment;
import rkr.binatestation.natterchat.fragments.UsersListFragment;
import rkr.binatestation.natterchat.listeners.ChatContactFragmentListener;
import rkr.binatestation.natterchat.listeners.RegistrationListener;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.SessionUtils;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_USERS;

public class HomeActivity extends BaseActivity implements RegistrationListener, ChatContactFragmentListener {

    private Intent mSettingsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            loadRegistrationFragment();
        } else {
            loadChatContactListFragment(new UserModel(user));
        }
    }

    private void loadRegistrationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RegistrationFragment.newInstance())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            signOut();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            navigateToSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToSettings() {
        startActivity(getSettingsIntent());
    }

    private Intent getSettingsIntent() {
        if (mSettingsIntent == null) {
            mSettingsIntent = new Intent(this, SettingsActivity.class);
        }
        return mSettingsIntent;
    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SplashScreenActivity.class));
        finish();
    }

    @Override
    public void onSuccessRegistration(FirebaseUser user) {

        UserModel userModel = new UserModel(user);
        userModel.setPushToken(SessionUtils.getPushToken(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child(KEY_TABLE_USERS).child(userModel.getId()).setValue(userModel);
        loadChatContactListFragment(userModel);
    }

    private void loadChatContactListFragment(UserModel userModel) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ChatContactListFragment.newInstance(userModel))
                .commit();

    }

    @Override
    public void onRegistrationFailed() {

    }

    @Override
    public void onActionUserList() {
        openUserListFragment();
    }

    private void openUserListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UsersListFragment.newInstance())
                .addToBackStack("")
                .commit();
    }
}
