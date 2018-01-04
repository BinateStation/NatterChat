package rkr.binatestation.natterchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

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
 * UsersSwipeListFragment
 */

public class UsersSwipeListFragment extends SwipeListFragment implements ValueEventListener, OnListItemClickListener {
    public static final String TAG = "UsersSwipeListFragment";
    private static final String KEY_CHAT_CONTACT_MODEL = "CHAT_CONTACT_MODEL";
    private ChatContactModel mChatContactModel;

    public static UsersSwipeListFragment newInstance(ChatContactModel chatContactModel) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_CHAT_CONTACT_MODEL, chatContactModel);
        UsersSwipeListFragment fragment = new UsersSwipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mChatContactModel = bundle.getParcelable(KEY_CHAT_CONTACT_MODEL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAdapter().setData(EmptyStateModel.getEmptyDataModels(EmptyStateModel.getUnKnownEmptyModel()));
        loadUsers();
    }

    private void loadUsers() {
        showProgress();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_USERS)
                .limitToFirst(100);
        usersQuery.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot + "]");
        hideProgress();
        ArrayList<Object> data = new ArrayList<>();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChatContactModel != null && firebaseUser != null) {
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                UserModel model = userSnapshot.getValue(UserModel.class);
                if (model != null && !firebaseUser.getUid().equals(model.getId())) {
                    data.add(model);
                }
            }
            getAdapter().setData(data);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, "onCancelled: ", databaseError.toException());
        hideProgress();
    }

    @Override
    public void onClickItem(Object object, int position, View actionView) {
        navigateToChatActivity(object);
    }

    private void navigateToChatActivity(Object object) {
        if (object instanceof UserModel) {
            UserModel receiver = (UserModel) object;
            if (mChatContactModel != null && getContext() != null) {
                mChatContactModel.setId(receiver.getId());
                mChatContactModel.setName(receiver.getName());
                mChatContactModel.setReceiver(receiver);
                mChatContactModel.setChatMessages(new ArrayList<ChatMessageModel>());
                startActivity(ChatActivity.newInstance(getContext(), mChatContactModel));
            }
        }
    }
}
