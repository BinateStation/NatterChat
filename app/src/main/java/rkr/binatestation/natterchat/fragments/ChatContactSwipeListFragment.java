package rkr.binatestation.natterchat.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.activities.ChatActivity;
import rkr.binatestation.natterchat.listeners.ChatContactFragmentListener;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.UserModel;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_USERS;

/**
 * Fragment to list the contact list
 */
public class ChatContactSwipeListFragment extends SwipeListFragment implements ValueEventListener, View.OnClickListener {
    private static final String TAG = "ChatContactSwipeListFra";
    private static final String KEY_USER_MODEL = "USER_MODEL";

    private ChatContactFragmentListener mListener;
    private UserModel mUserModel;

    public ChatContactSwipeListFragment() {
        // Required empty public constructor
    }

    public static ChatContactSwipeListFragment newInstance(UserModel userModel) {
        Log.d(TAG, "newInstance() called with: userModel = [" + userModel + "]");
        Bundle args = new Bundle();
        args.putParcelable(KEY_USER_MODEL, userModel);
        ChatContactSwipeListFragment fragment = new ChatContactSwipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatContactFragmentListener) {
            mListener = (ChatContactFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserModel = bundle.getParcelable(KEY_USER_MODEL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View actionUserListView = view.findViewById(R.id.action_pick_contact);
        actionUserListView.setOnClickListener(this);
        loadUser();
    }

    private void loadUser() {
        showProgress();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_USERS)
                .child(mUserModel.getId());
        usersQuery.keepSynced(true);
        usersQuery.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot + "]");
        hideProgress();
        ArrayList<ChatContactModel> data = new ArrayList<>();
        UserModel userModel = dataSnapshot.getValue(UserModel.class);
//        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//            ChatContactModel chatContactModel = userSnapshot.getValue(ChatContactModel.class);
//            if(!data.contains(chatContactModel)) {
//                data.add(userSnapshot.getValue(ChatContactModel.class));
//            }
//        }
//        getAdapter().setData(data);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, "onCancelled: ", databaseError.toException());
        hideProgress();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_pick_contact) {
            if (mListener != null) {
                mListener.onActionUserList();
            }
        }
    }

    @Override
    public void onClickItem(Object object, int position, View actionView) {
        super.onClickItem(object, position, actionView);
        if (object instanceof ChatContactModel) {
            ChatContactModel chatContactModel = (ChatContactModel) object;
            navigateToChatActivity(chatContactModel);
        }
    }

    private void navigateToChatActivity(ChatContactModel chatContactModel) {
        startActivity(ChatActivity.newInstance(getContext(), chatContactModel));
    }
}
