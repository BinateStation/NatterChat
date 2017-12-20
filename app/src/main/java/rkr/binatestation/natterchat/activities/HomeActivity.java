package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.ChatContactSwipeListFragment;
import rkr.binatestation.natterchat.fragments.UsersSwipeListFragment;
import rkr.binatestation.natterchat.listeners.ChatContactFragmentListener;
import rkr.binatestation.natterchat.models.UserModel;

public class HomeActivity extends BaseActivity implements ChatContactFragmentListener {

    private Intent mSettingsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            signOut();
        } else {
            loadChatContactListFragment(new UserModel(user));
        }
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
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            UserModel userModel = null;
            if (firebaseUser != null) {
                userModel = new UserModel(firebaseUser);
            }
            mSettingsIntent = DetailsActivity.newInstance(this, userModel);
        }
        return mSettingsIntent;
    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SplashScreenActivity.class));
        finish();
    }


    private void loadChatContactListFragment(UserModel userModel) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ChatContactSwipeListFragment.newInstance(userModel))
                .commit();

    }

    @Override
    public void onActionUserList() {
        openUserListFragment();
    }

    private void openUserListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UsersSwipeListFragment.newInstance())
                .addToBackStack("")
                .commit();
    }
}
