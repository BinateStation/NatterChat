package rkr.binatestation.natterchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.ListAdapter;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.UserModel;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_USERS;

public class HomeActivity extends BaseActivity implements ValueEventListener {

    private Intent mSettingsIntent;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getAdapter());
        getAdapter().add(EmptyStateModel.getUnKnownEmptyModel());
        loadContacts();
    }

    private ListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ListAdapter(this);
        }
        return mAdapter;
    }

    private void loadContacts() {
        showProgress(mSwipeRefreshLayout);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_USERS).limitToFirst(100);
        usersQuery.addValueEventListener(this);

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
    public void onDataChange(DataSnapshot dataSnapshot) {
        hideProgress(mSwipeRefreshLayout);
        ArrayList<UserModel> userModels = new ArrayList<>();
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
            getAdapter().add(userSnapshot.getValue(UserModel.class));
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        hideProgress(mSwipeRefreshLayout);
    }
}
