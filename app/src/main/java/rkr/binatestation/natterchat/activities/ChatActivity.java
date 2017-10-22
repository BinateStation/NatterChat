package rkr.binatestation.natterchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.ListFragment;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.Status;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.SessionUtils;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_CHATS;

public class ChatActivity extends BaseActivity implements View.OnClickListener, ValueEventListener {

    private static final String KEY_CHAT_CONTACT_MODEL = "CHAT_CONTACT_MODEL";
    private static final String TAG = "ChatActivity";
    private ListFragment mListFragment;
    private EditText mFieldMessageEditText;
    private ChatContactModel mChatContactModel;

    public static Intent newInstance(Context context, ChatContactModel chatContactModel) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(KEY_CHAT_CONTACT_MODEL, chatContactModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setBackActionEnabled();
        mChatContactModel = getIntent().getParcelableExtra(KEY_CHAT_CONTACT_MODEL);
        mFieldMessageEditText = findViewById(R.id.field_message);
        View actionSendMessage = findViewById(R.id.action_send_message);
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        if (mListFragment != null) {
            mListFragment.getLinearLayoutManager().setStackFromEnd(true);
            loadContacts();
        }

        actionSendMessage.setOnClickListener(this);
    }

    private void loadContacts() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query usersQuery = mDatabase.child(KEY_TABLE_CHATS)
                .child(mChatContactModel.getId())
                .child("chatMessages")
                .limitToFirst(100);
        usersQuery.keepSynced(true);
        usersQuery.addValueEventListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mChatContactModel != null) {
            SessionUtils.setChatResumed(
                    this,
                    true,
                    mChatContactModel.getReceiver().getId()
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionUtils.setChatResumed(this, false, "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_send_message) {
            readInput();
        }
    }


    private void readInput() {
        if (mFieldMessageEditText != null) {
            String message = mFieldMessageEditText.getText().toString().trim();
            if (TextUtils.isEmpty(message)) {
                mFieldMessageEditText.setError(getString(R.string.type_a_message));
                mFieldMessageEditText.requestFocus();
            } else {
                actionSendMessage(message);
            }
        }
    }

    private void actionSendMessage(String message) {
        long time = Calendar.getInstance().getTimeInMillis();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserModel sender = new UserModel(user);
            ChatMessageModel chatMessageModel = new ChatMessageModel(
                    sender.getId() + time,
                    message,
                    time,
                    Status.PENDING.getValue(),
                    sender
            );
            mFieldMessageEditText.setText("");
            addMessage(chatMessageModel);
            sendMessage(chatMessageModel);
        }
    }

    private void addMessage(ChatMessageModel chatMessageModel) {
        if (mListFragment != null) {
            mListFragment.getAdapter().add(chatMessageModel);
            mListFragment.scrollToEnd();
        }
    }

    private void sendMessage(ChatMessageModel chatMessageModel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        chatMessageModel.setStatus(Status.SEND.getValue());
        mChatContactModel.getChatMessages().add(chatMessageModel);
        myRef.child(KEY_TABLE_CHATS).child(mChatContactModel.getId()).setValue(mChatContactModel);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (mListFragment != null) {
            ArrayList<Object> data = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                data.add(snapshot.getValue(ChatMessageModel.class));
            }
            if (data.size() > 0) {
                mListFragment.getAdapter().setData(data);
            } else {
                mListFragment.getAdapter().addEmptyState(EmptyStateModel.getChatMessageEmptyState());
            }
            mListFragment.scrollToEnd();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
