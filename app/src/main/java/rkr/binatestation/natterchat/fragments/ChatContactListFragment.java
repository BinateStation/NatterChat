package rkr.binatestation.natterchat.fragments;


import android.content.Context;
import android.os.Bundle;
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
import rkr.binatestation.natterchat.listeners.OnListItemClickListener;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.UserModel;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_CHATS;

/**
 * Fragment to list the contact list
 */
public class ChatContactListFragment extends ListFragment implements ValueEventListener, View.OnClickListener, OnListItemClickListener {
    private static final String TAG = "ChatContactListFragment";
    private static final String KEY_USER_MODEL = "USER_MODEL";

    private ChatContactFragmentListener mListener;
    private UserModel mUserModel;

    public ChatContactListFragment() {
        // Required empty public constructor
    }

    public static ChatContactListFragment newInstance(UserModel userModel) {
        Log.d(TAG, "newInstance() called with: userModel = [" + userModel + "]");
        Bundle args = new Bundle();
        args.putParcelable(KEY_USER_MODEL, userModel);
        ChatContactListFragment fragment = new ChatContactListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View actionUserListView = view.findViewById(R.id.action_pick_contact);
        actionUserListView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        showProgress();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_CHATS)
                .orderByChild("id")
                .startAt(mUserModel.getId())
                .endAt("\uf8ff" + mUserModel.getId() + "\uf8ff");
        usersQuery.keepSynced(true);
        usersQuery.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot + "]");
        hideProgress();
        ArrayList<Object> data = new ArrayList<>();
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
            data.add(userSnapshot.getValue(ChatContactModel.class));
        }
        if (data.size() > 0) {
            getAdapter().setData(data);
        } else {
            getAdapter().add(EmptyStateModel.getChatContactEmptyState());
        }
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
    public void onClickItem(Object object, int position) {
        if (object instanceof ChatContactModel) {
            ChatContactModel chatContactModel = (ChatContactModel) object;
            navigateToChatActivity(chatContactModel);
        }
    }

    private void navigateToChatActivity(ChatContactModel chatContactModel) {
        startActivity(ChatActivity.newInstance(getContext(), chatContactModel));
    }
}
