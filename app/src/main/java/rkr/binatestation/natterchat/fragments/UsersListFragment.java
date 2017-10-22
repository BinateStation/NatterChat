package rkr.binatestation.natterchat.fragments;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rkr.binatestation.natterchat.activities.ChatActivity;
import rkr.binatestation.natterchat.listeners.OnListItemClickListener;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.UserModel;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_USERS;

/**
 * Created by RKR on 22-10-2017.
 * UsersListFragment
 */

public class UsersListFragment extends ListFragment implements ValueEventListener, OnListItemClickListener {
    private static final String TAG = "UsersListFragment";

    public static UsersListFragment newInstance() {

        Bundle args = new Bundle();

        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAdapter().add(EmptyStateModel.getUnKnownEmptyModel());
        loadUsers();
    }

    private void loadUsers() {
        showProgress();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_USERS).limitToFirst(100);
        usersQuery.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot + "]");
        hideProgress();
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
            getAdapter().add(userSnapshot.getValue(UserModel.class));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, "onCancelled: ", databaseError.toException());
        hideProgress();
    }

    @Override
    public void onClickItem(Object object, int position) {
        navigateToChatActivity(object);
    }

    private void navigateToChatActivity(Object object) {
        if (object instanceof UserModel) {
            UserModel receiver = (UserModel) object;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                UserModel sender = new UserModel(user);
                ChatContactModel chatContactModel = new ChatContactModel(
                        sender.getId() + receiver.getId(),
                        receiver.getName(),
                        sender,
                        receiver,
                        new ArrayList<ChatMessageModel>()
                );
                startActivity(ChatActivity.newInstance(getContext(), chatContactModel));
            }
        }
    }
}
